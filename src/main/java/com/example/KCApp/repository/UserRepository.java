package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername( String username );
}

