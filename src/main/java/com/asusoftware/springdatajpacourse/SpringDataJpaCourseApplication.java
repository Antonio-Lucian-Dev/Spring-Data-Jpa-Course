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
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
		return args -> {

			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstName, lastName);
			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setEmail(email);
			student.setAge(faker.number().numberBetween(17, 55));

			StudentIdCard studentIdCard = new StudentIdCard();
			studentIdCard.setCardNumber("123456789");
			studentIdCard.setStudent(student);
			// Salva lo student e il studentIdCard perche abbiamo messo Cascade.typeAll
			studentIdCardRepository.save(studentIdCard);
			studentRepository.findById(1L).ifPresent(System.out::println);
			studentIdCardRepository.findById(1L).ifPresent(System.out::println);

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
