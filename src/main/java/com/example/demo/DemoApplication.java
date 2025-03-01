package com.example.demo;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Integer> ll = Arrays.asList(1, 2, 55, 2, 5, 8);
		 Integer a = ll.stream().sorted().distinct().skip(ll.size() - 3).findFirst().get();
		System.out.println(a);
	 

	}

}
