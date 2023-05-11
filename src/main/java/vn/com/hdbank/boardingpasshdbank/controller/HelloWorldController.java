package vn.com.hdbank.boardingpasshdbank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.entity.HelloWorld;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.service.impl.HelloWorldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/hello-world")
public class HelloWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);
    private static final String RESPONSE = "Response: {}";
    @Autowired
    private HelloWorldServiceImpl helloWorldService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInfo<HelloWorld>> getAllHelloWorld(@PathVariable int id) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorldService.findById(id))
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseInfo<List<HelloWorld>>> getHelloWorld() {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        ResponseInfo<List<HelloWorld>> response = ResponseInfo.<List<HelloWorld>>builder()
                .data(helloWorldService.findAll())
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseInfo<HelloWorld>> createHelloWorld(@RequestBody HelloWorld helloWorld) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        helloWorldService.create(helloWorld);
        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorld)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInfo<HelloWorld>> updateHelloWorld(@RequestBody HelloWorld helloWorld,
                                                                     @PathVariable int id) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        helloWorldService.update(helloWorld, id);
        ResponseInfo<HelloWorld> response = ResponseInfo.<HelloWorld>builder()
                .data(helloWorld)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInfo<Integer>> deleteHelloWorld(@PathVariable int id) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        helloWorldService.delete(id);
        ResponseInfo<Integer> response = ResponseInfo.<Integer>builder()
                .data(id)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

