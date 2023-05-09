package vn.com.hdbank.boardingpasshdbank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
public class BaseService {
    @Autowired
    protected ModelMapper modelMapper;
}
