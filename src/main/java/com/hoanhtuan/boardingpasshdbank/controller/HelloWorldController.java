package com.hoanhtuan.boardingpasshdbank.controller;

import com.hoanhtuan.boardingpasshdbank.common.Constant;
import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseStatus;
import com.hoanhtuan.boardingpasshdbank.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/helloworld")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStatus> getAllHelloWorld(@PathVariable int id) {
        HelloWorld helloWorld = helloWorldService.findById(id);

        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Successfully!")
                .responseCode(Constant.OK)
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseStatus> getHelloWorld() {
        List<HelloWorld> helloWorlds = helloWorldService.findAll();

        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorlds)
                .responseMessage("Successfully!")
                .responseCode(Constant.OK)
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseStatus> createHelloWorld(@RequestBody HelloWorld helloWorld) {
        helloWorldService.create(helloWorld);


        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Create Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStatus> createHelloWorld(@RequestBody HelloWorld helloWorld,@PathVariable int id) {
        helloWorldService.update(helloWorld,id);


        ResponseStatus response = ResponseStatus.builder()
                .data(helloWorld)
                .responseMessage("Created Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> deleteHelloWorld(@PathVariable int id) {
        helloWorldService.delete(id);


        ResponseStatus response = ResponseStatus.builder()
                .data(id)
                .responseMessage("Deleted Successfully")
                .responseCode(Constant.OK)
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
