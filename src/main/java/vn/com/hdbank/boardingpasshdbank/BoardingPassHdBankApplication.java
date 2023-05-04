package vn.com.hdbank.boardingpasshdbank;

import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardingPassHdBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardingPassHdBankApplication.class, args);
		WriteLog.infoLog("Run App Successful");
	}
}
