package com.hoanhtuan.boardingpasshdbank.service;

import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;

import java.util.List;

public interface HelloWorldService {
     HelloWorld findById(int id);
     List<HelloWorld> findAll();
     void create(HelloWorld helloWorld);
     void update(HelloWorld helloWorld,int id);
     void delete(int id);
}
