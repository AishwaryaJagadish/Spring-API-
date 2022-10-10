package com.todo.Todos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.todo.Todos.model.Todo;

public interface Repo extends MongoRepository<Todo,String> {
	
	

}
