package vn.com.hdbank.boardingpasshdbank.service.impl;

import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.HelloWorld;
import vn.com.hdbank.boardingpasshdbank.repository.HelloWorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;

import java.util.List;

@Service
public class HelloWorldServiceImpl extends BaseService {

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

    public void update(HelloWorld helloWorld, int id) {
        HelloWorld updateHello = helloWorldRepository.findById(id);

        if (updateHello != null) {
            updateHello.setId(id);
            updateHello.setContent(helloWorld.getContent());
            helloWorldRepository.update(updateHello);
        } else {
            throw new CustomException(ApiResponseStatus.NOT_FOUND);
        }

    }

    public void delete(int id) {
        helloWorldRepository.delete(id);
    }
}
