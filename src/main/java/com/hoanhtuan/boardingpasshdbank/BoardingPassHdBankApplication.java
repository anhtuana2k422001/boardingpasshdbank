package com.hoanhtuan.boardingpasshdbank;

import com.hoanhtuan.boardingpasshdbank.utils.WriteLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardingPassHdBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardingPassHdBankApplication.class, args);
		WriteLog.write("Run App Successful");
	}

}
