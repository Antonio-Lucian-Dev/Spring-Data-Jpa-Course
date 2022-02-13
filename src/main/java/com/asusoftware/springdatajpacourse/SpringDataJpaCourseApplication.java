package com.asusoftware.springdatajpacourse;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
			/*
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

			// delete a user
			System.out.println("Delete student using @Modifying and @Transaction " + studentRepository.deleteStudentById(3L));

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

           generateRandomStudents(studentRepository);
           // sorting(studentRepository);
			// -----------------------
			// Pagination and sorting section
			// PageRequest.of(numeroPagina, quanti elementi ritornare per pagina, sorting)
			PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "firstName"));
			Page<Student> page = studentRepository.findAll(pageRequest);
			System.out.println(page);
		};
	}

	private void sorting(StudentRepository studentRepository) {
		// Posso ordinare in base al firstName in ordine ascendente, posso fare inoltre piu sorting
		// con .and alla fine di Sort.By(...).and
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
		studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName()));
	}

	private void generateRandomStudents(StudentRepository studentRepository) {
		// Using faker per importare dati random sul nostro db
		Faker faker = new Faker();
		for (int i = 0; i<= 20; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstName, lastName);
			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setEmail(email);
			student.setAge(faker.number().numberBetween(17, 55));
			studentRepository.save(student);
		}
	}
}
