package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		Thread.sleep(1000);

		new ProcessBuilder("cmd", "/c", "start", "chrome", "http://localhost:8080").start();
	}
}