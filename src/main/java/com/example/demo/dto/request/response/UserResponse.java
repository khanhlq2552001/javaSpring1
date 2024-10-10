package com.example.demo.dto.request.response;

import com.example.demo.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    Set<Role> roles;
}
