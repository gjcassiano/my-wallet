package com.picpay.wallet;

import com.picpay.wallet.utils.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@SpringBootApplication
public class WalletApplication {
	public static void main(String[] args) {
		PropertiesUtils.initProperties();

		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}


		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(WalletApplication.class, args);
	}
}
