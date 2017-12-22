package cn.geekview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class GeekAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekAnalysisApplication.class, args);
	}
}
