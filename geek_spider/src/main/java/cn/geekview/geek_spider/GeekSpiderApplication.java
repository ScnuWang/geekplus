package cn.geekview.geek_spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.geekview.geek_spider.entity.mapper")
public class GeekSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekSpiderApplication.class, args);
	}
}
