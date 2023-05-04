package com.hoanhtuan.boardingpasshdbank.controller;

import com.hoanhtuan.boardingpasshdbank.common.Constant;
import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseStatus;
import com.hoanhtuan.boardingpasshdbank.service.impl.HelloWorldServiceImpl;
import com.hoanhtuan.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hello-world")
public class HelloWorldController {
    private static final String CLASS_NAME  = PassengerController.class.getName();
    @Autowired
    private HelloWorldServiceImpl helloWorldServiceImpl;

    @GetMapping("")
    public ResponseEntity<ResponseStatus> getAllHelloWorld() {
        String methodName = "getAllHelloWorld";
        List<HelloWorld> helloWorlds = helloWorldServiceImpl.findAll();
        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorlds)
                .responseMessage("Successfully!")
                .responseCode(Constant.OK)
                .build();
        // logging here
        WriteLog.infoLog(CLASS_NAME, methodName, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStatus> getHelloWorld(@PathVariable int id) {
        String methodName = "getHelloWorld";
        HelloWorld helloWorld = helloWorldServiceImpl.findById(id);
        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Successfully!")
                .responseCode(Constant.OK)
                .build();
        // logging here
        WriteLog.infoLog(CLASS_NAME, methodName, helloWorld);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseStatus> createHelloWorld(@RequestBody HelloWorld helloWorld) {
        String methodName = "createHelloWorld";
        helloWorldServiceImpl.create(helloWorld);
        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Create Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        WriteLog.infoLog(CLASS_NAME, methodName, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStatus> updateHelloWorld(@RequestBody HelloWorld helloWorld,@PathVariable int id) {
        String methodName = "updateHelloWorld";
        helloWorldServiceImpl.update(helloWorld,id);
        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Created Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        WriteLog.infoLog(CLASS_NAME, methodName, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> deleteHelloWorld(@PathVariable int id) {
        String methodName = "deleteHelloWorld";
        helloWorldServiceImpl.delete(id);
        ResponseStatus response = ResponseStatus.builder()
                .data(id)
                .responseMessage("Deleted Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        WriteLog.infoLog(CLASS_NAME, methodName, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
