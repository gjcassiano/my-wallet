package com.wallet;

import com.wallet.utils.PropertiesUtils;
import lombok.extern.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class WalletApplication {
	public static void main(String[] args) {
		PropertiesUtils.initProperties();

		try {
			log.info("Host IP: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}


		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(WalletApplication.class, args);
	}
}
