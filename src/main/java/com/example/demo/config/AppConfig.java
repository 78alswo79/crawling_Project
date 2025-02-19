package com.example.demo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public ExecutorService excutorService() {
		// 고정된 스레드 풀을 생성하고, 필요한 스레드 수에 맞게 조정
		return Executors.newFixedThreadPool(4);
	}
}
