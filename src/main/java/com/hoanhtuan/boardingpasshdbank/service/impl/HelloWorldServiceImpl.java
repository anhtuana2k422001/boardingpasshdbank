package com.hoanhtuan.boardingpasshdbank.service.impl;

import com.hoanhtuan.boardingpasshdbank.exception.NotFoundException;
import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;
import com.hoanhtuan.boardingpasshdbank.repository.HelloWorldRepository;
import com.hoanhtuan.boardingpasshdbank.service.HelloWorldService;
import com.hoanhtuan.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    private static final String CLASS_NAME  = TicketVietJetServiceImpl.class.getName();
    @Autowired
    private HelloWorldRepository helloWorldRepository;

    public List<HelloWorld> findAll() {
        return helloWorldRepository.findAll();
    }

    public HelloWorld findById(int id) {
        String methodName = "findById";
        try {
            return helloWorldRepository.findById(id);
        }catch(DataAccessException ex){
            WriteLog.errorLog(CLASS_NAME, methodName, ex.getMessage());
            throw new NotFoundException("No HellWorld found for update with id: " + id);
        }
    }

    public void create(HelloWorld helloWorld) {
        helloWorld.setContent(helloWorld.getContent());
        helloWorldRepository.create(helloWorld);
    }

    public void update(HelloWorld helloWorld,int id) {
        String methodName = "update";
        HelloWorld updateHelloWorld = helloWorldRepository.findById(id);
        if (updateHelloWorld == null) {
            WriteLog.errorLog(CLASS_NAME, methodName, "No HellWorld found for update with id:" + id);
            throw new NotFoundException("No HellWorld found for update with id: " + id);
        }
        updateHelloWorld.setId(id);
        updateHelloWorld.setContent(helloWorld.getContent());
        try {
            helloWorldRepository.update(helloWorld);
        }catch(DataAccessException  ex){
            WriteLog.errorLog(CLASS_NAME, methodName, ex.getMessage());
            throw new NotFoundException("No HellWorld found with id: " + id);
        }
    }

    public void delete(int id) {
        String methodName = "delete";
        try {
            helloWorldRepository.delete(id);
        }catch(DataAccessException  ex){
            WriteLog.errorLog(CLASS_NAME, methodName, ex.getMessage());
            throw new NotFoundException("No HellWorld found with id: " + id);
        }
    }
}
