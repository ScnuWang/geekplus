package cn.geekview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.geekview.entity.mapper")
public class GeekWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekWebsiteApplication.class, args);
	}
}
