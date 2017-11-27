package cn.geekview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.geekview.entity.mapper")
public class GeekAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekAnalysisApplication.class, args);
	}
}
