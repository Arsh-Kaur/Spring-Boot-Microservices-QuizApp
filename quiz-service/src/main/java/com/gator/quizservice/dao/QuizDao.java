package com.gator.quizservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gator.quizservice.model.Quiz;


public interface QuizDao extends JpaRepository<Quiz,Integer>{

}
