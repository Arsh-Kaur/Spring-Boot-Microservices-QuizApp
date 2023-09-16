package com.gator.quizservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gator.quizservice.dao.QuizDao;
import com.gator.quizservice.feign.QuizInterface;
import com.gator.quizservice.model.QuestionWrapper;
import com.gator.quizservice.model.Quiz;
import com.gator.quizservice.model.Response;

@Service
public class QuizService {
	@Autowired
	QuizDao quizDao;
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
		
		//Call the generate url of the question service
		//returns id of the questions
		//we can send a request to other service using RestTemplate - http://localhost:8080/question/generate - we dont want to hardcode ip address and port number
		//FeignClient - provides a way to request other service and we dont need to hardcode all values -> 
		//Service Discovery - questionService needs to be discoverable. 
		// We need to use some server using which we can find the microservice. one of the famous ones is Netflix's Eureka Server.
		
		
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("success", HttpStatus.CREATED) ;
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz = quizDao.findById(id).get();
		List<Integer> questionIdsFromDB = quiz.getQuestionIds();
		ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromId(questionIdsFromDB);
		return questionsForUser;
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		return quizInterface.getScore(responses);
	}
	
	

}
