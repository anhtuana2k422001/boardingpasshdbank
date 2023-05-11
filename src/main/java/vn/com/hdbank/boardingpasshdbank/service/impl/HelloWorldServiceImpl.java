package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER  = LoggerFactory.getLogger(HelloWorldServiceImpl.class);
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
            LOGGER.error(ApiResponseStatus.NOT_FOUND.getStatusMessage());
            throw new CustomException(ApiResponseStatus.NOT_FOUND);
        }

    }

    public void delete(int id) {
        helloWorldRepository.delete(id);
    }
}
