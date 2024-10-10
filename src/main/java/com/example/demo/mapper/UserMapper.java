package com.example.demo.mapper;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.request.response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    //@Mapping(source = "firstName", target = "lastName")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void UpdateUser(@MappingTarget  User user, UserUpdateRequest request);
}
