package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
//		List<Integer> ll = Arrays.asList(1, 2, 55, 2, 5, 8);
//		 Integer a = ll.stream().sorted().distinct().skip(ll.size() - 3).findFirst().get();
//		System.out.println(a);
	 List<Integer>ll=Arrays.asList(1,2,3,4,5);
//	 List<Integer>even=ll.stream().filter((i)-> i%2==0).collect(Collectors.toList());
//	 System.out.println(even);
//List<Integer>greate=ll.stream().filter(i->i>5).collect(Collectors.toList());
//System.out.println(greate);
	 int a=ll.stream().sorted().distinct().skip(ll.size()-2).findFirst().get();
	 System.out.println(a);
	}

}
