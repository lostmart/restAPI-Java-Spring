package com.chaptoporg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chaptoporg.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    public User findByEmail(String email);
}
