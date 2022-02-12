package com.asusoftware.springdatajpacourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Query method(Custom)
    @Query("SELECT s from Student s WHERE s.email = ?1") // Permette di fare query native, ?1 perche ho solo un argomento in questa funzione
    Optional<Student> findStudentByEmail(String email);

    // Ci possiamo riferire ai parametri con numeri, ?1 indica il primo parametro, ?2 il secondo e cosi via..
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    // Native Query(Query che sono native per il Database rispettivo, ad es postgresql)
    @Query(value = "SELECT * FROM student s WHERE s.first_name = ?1 AND s.age >= ?2", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(String firstName, Integer age);


    // Il problema delle native query e che se un domani cambiamo database ad es MySql, il nostro query non
    // e piu buona, non funzionera mai
    // Invece la Query con JPQL e buona perche non e specifica a database
}
