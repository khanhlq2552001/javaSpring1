package com.example.demo.repository;

import com.example.demo.entity.InvalidateToken;
import com.example.demo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidateToken, String> {

}
