package vn.com.hdbank.boardingpasshdbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardingPassHdBankApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardingPassHdBankApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BoardingPassHdBankApplication.class, args);
		LOGGER.info("Run App Successful");
	}
}
