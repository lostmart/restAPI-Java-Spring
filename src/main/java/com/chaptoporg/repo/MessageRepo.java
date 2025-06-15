package com.chaptoporg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chaptoporg.model.Message;

public interface MessageRepo extends JpaRepository<Message, Integer> {

}