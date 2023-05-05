package vn.com.hdbank.boardingpasshdbank.controller;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.helloworld.HelloWorld;
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
    public ResponseEntity<ResponseInfo> getAllHelloWorld(@PathVariable int id) {
        HelloWorld helloWorld = helloWorldService.findById(id);

        ResponseInfo response = ResponseInfo.builder()
                .data(helloWorld)
                .statusCode(ApiResponseStatus.SUCCESS.getStatusCode())
                .statusMessage(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseInfo> getHelloWorld() {
        List<HelloWorld> helloWorlds = helloWorldService.findAll();

        ResponseInfo response = ResponseInfo.builder()
                .data(helloWorlds)
                .statusCode(ApiResponseStatus.SUCCESS.getStatusCode())
                .statusMessage(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseInfo> createHelloWorld(@RequestBody HelloWorld helloWorld) {
        helloWorldService.create(helloWorld);


        ResponseInfo response = ResponseInfo.builder()
                .data(helloWorld)
                .statusCode(ApiResponseStatus.SUCCESS.getStatusCode())
                .statusMessage(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInfo> createHelloWorld(@RequestBody HelloWorld helloWorld, @PathVariable int id) {
        helloWorldService.update(helloWorld,id);


        ResponseInfo response = ResponseInfo.builder()
                .data(helloWorld)
                .statusCode(ApiResponseStatus.CREATED.getStatusCode())
                .statusMessage(ApiResponseStatus.CREATED.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInfo> deleteHelloWorld(@PathVariable int id) {
        helloWorldService.delete(id);


        ResponseInfo response = ResponseInfo.builder()
                .data(id)
                .statusCode(ApiResponseStatus.DELETED.getStatusCode())
                .statusMessage(ApiResponseStatus.DELETED.getStatusMessage())
                .build();
        // logging here
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
