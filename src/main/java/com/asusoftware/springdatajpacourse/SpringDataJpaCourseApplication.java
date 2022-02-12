package com.asusoftware.springdatajpacourse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaCourseApplication.class, args);
	}

	@Bean
	// Ci permette di eseguire il codice quando l'app si starta
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student maria = new Student();
			maria.setFirstName("Maria");
			maria.setLastName("Jones");
			maria.setEmail("maria@gmail.com");
			maria.setAge(23);

			Student maria2 = new Student();
			maria2.setFirstName("Maria");
			maria2.setLastName("Jones");
			maria2.setEmail("maria2@gmail.com");
			maria2.setAge(25);

			Student jake = new Student();
			jake.setFirstName("Jake");
			jake.setLastName("Ali");
			jake.setEmail("jake@gmail.com");
			jake.setAge(30);
			// studentRepository.save(jake);

			System.out.println("Adding Maria and Jake");
			studentRepository.saveAll(List.of(maria, jake, maria2));

			studentRepository.findStudentByEmail("jake@gmail.com")
					.ifPresentOrElse(System.out::println,
							() -> System.out.println("Student with email jake@gmail.com not found"));

			studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("Maria", 23)
					.forEach(System.out::println);

			studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThan("Maria", 18)
					.forEach(System.out::println);

			/*
			System.out.print("Number of Students: ");
			System.out.println(studentRepository.count()); // Quanti Studenti ci sono

			// id 2, L significa long, perche e di tipo long
			studentRepository
					.findById(2L)
					.ifPresentOrElse(
							System.out::println,
							() -> {
								System.out.println("Student with ID 2 not found!");
							});

			System.out.println("Select all Students");
			List<Student> students = studentRepository.findAll();
			students.forEach(System.out::println);

			System.out.println("Delete Maria");
			studentRepository.deleteById(1L);

			System.out.print("Number of Students: ");
			System.out.println(studentRepository.count()); */
		};
	}
}
