package vn.com.hdbank.boardingpasshdbank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BoardingPassHdBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardingPassHdBankApplication.class, args);
		LOGGER.info("Run App Successful");
	}
}
