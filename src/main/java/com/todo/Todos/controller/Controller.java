package com.todo.Todos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.Todos.model.Todo;
import com.todo.Todos.repository.Repo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private Repo repo;
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody Todo todo){
		try {
			repo.save(todo);
			return new ResponseEntity<Todo>(todo,HttpStatus.OK);
			}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping("/todos")
	public ResponseEntity<?> getTodo(){
		logger.info("Entered getmapping");
		try {
			
			List<Todo> gtodo = repo.findAll();
			logger.info("Got object {}", gtodo);
			return new ResponseEntity<List<Todo>>(gtodo,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateTodo(@RequestBody Todo todo,@PathVariable String id){
		try {
			Optional<Todo> todooptional = repo.findById(id);
			if(todooptional.isPresent()) {
			  Todo newobj = todooptional.get();
			  newobj.setDesc(todo.getDesc());
			  newobj.setCompleted(todo.isCompleted());
			  repo.deleteById(id);
			  repo.save(todo);
			  return new ResponseEntity<>("Updated the todo in database",HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>("todo not found in the database",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id){
		try {
			Optional<Todo> todooptional = repo.findById(id);
			if(todooptional.isPresent()) {
			  repo.deleteById(id);
			  return new ResponseEntity<>("Deleted the document in database",HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>("todo not found in the database",HttpStatus.NOT_FOUND);
			}
			
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleUser(@PathVariable String id)
	{
		
		logger.info("Entered getmapping 2");
		try {
		Optional<Todo> todooptional = repo.findById(id);
		if(todooptional.isPresent()) {
			logger.info("Got object found {}", todooptional.get());
			return new ResponseEntity<Todo>(todooptional.get(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("todo not found in the database",HttpStatus.NOT_FOUND);
		}
	  }
	catch(Exception e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	  }
	}
}
