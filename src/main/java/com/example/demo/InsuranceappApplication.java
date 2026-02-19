//package com.example.demo;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class InsuranceappApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(InsuranceappApplication.class, args);
//	}
//
//}



package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InsuranceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceappApplication.class, args);
	}

	// --- THIS ADDS THE DEFAULT ADMIN USER ---
	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			// Check if 'admin' user exists in the database
			if (!userRepository.existsById("admin")) {
				User admin = new User();
				admin.setId("admin");
				admin.setName("System Master");
				admin.setEmail("admin@uplife.com");
				admin.setPassword("admin123"); // Default Password
				admin.setRole("admin");
				admin.setState("Telangana");
				admin.setDist("Hyderabad");
				admin.setMandal("Amberpet");
				admin.setCreatedBy("SYSTEM");
				
				userRepository.save(admin);
				System.out.println(">>> SUCCESS: DEFAULT ADMIN CREATED (ID: admin, PASS: admin123)");
			} else {
				System.out.println(">>> ADMIN ALREADY EXISTS. SKIPPING CREATION.");
			}
		};
	}
}