package vn.com.hdbank.boardingpasshdbank.controller;

import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.entity.HelloWorld;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.service.impl.HelloWorldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("api/helloworld")
public class HelloWorldController {
    @Autowired
    private HelloWorldServiceImpl helloWorldService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInfo<HelloWorld>> getAllHelloWorld(@PathVariable int id) {
        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorldService.findById(id))
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseInfo<List<HelloWorld>>> getHelloWorld() {
        ResponseInfo<List<HelloWorld>> response = ResponseInfo.<List<HelloWorld>>builder()
                .data(helloWorldService.findAll())
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseInfo<HelloWorld>> createHelloWorld(@RequestBody HelloWorld helloWorld) {
        helloWorldService.create(helloWorld);

        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorld)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInfo<HelloWorld>> updateHelloWorld(@RequestBody HelloWorld helloWorld, @PathVariable int id) {
        helloWorldService.update(helloWorld, id);

        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorld)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInfo<Integer>> deleteHelloWorld(@PathVariable int id) {
        helloWorldService.delete(id);

        ResponseInfo<Integer> response = ResponseInfo.<Integer>builder()
                .data(id)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

