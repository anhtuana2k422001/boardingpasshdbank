package com.hoanhtuan.boardingpasshdbank.repository;

import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;

import java.util.List;

public interface HelloWorldRepository {

    void create(HelloWorld helloWorld);

    HelloWorld findById(int id);

    List<HelloWorld> findAll();

    void update(HelloWorld helloWorld);

    void delete(int id);
}
