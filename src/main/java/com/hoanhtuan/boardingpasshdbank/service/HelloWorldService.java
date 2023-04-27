package com.hoanhtuan.boardingpasshdbank.service;

import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;
import com.hoanhtuan.boardingpasshdbank.repository.HelloWorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldRepository helloWorldRepository;

    public void create(HelloWorld helloWorld) {
        helloWorldRepository.create(helloWorld);
    }

    public HelloWorld findById(int id) {
        return helloWorldRepository.findById(id);
    }

    public List<HelloWorld> findAll() {
        return helloWorldRepository.findAll();
    }

    public void update(HelloWorld helloWorld,int id) {
        HelloWorld updateHello = new HelloWorld();
        updateHello.setId(id);
        updateHello.setContent(helloWorld.getContent());
        helloWorldRepository.update(updateHello);
    }

    public void delete(int id) {
        helloWorldRepository.delete(id);
    }
}
