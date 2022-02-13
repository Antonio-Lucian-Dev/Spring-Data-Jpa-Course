package com.asusoftware.springdatajpacourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Query method(Custom)
    @Query("SELECT s from Student s WHERE s.email = ?1") // Permette di fare query native, ?1 perche ho solo un argomento in questa funzione
    Optional<Student> findStudentByEmail(String email);

    // Ci possiamo riferire ai parametri con numeri, ?1 indica il primo parametro, ?2 il secondo e cosi via..
   // @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
   // List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);
    // Named parameters, possiamo usare i loro nomi invece dei numeri
    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.age >= :age")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(@Param("firstName") String firstName, @Param("age") Integer age);

    // Native Query(Query che sono native per il Database rispettivo, ad es postgresql)
    @Query(value = "SELECT * FROM student s WHERE s.first_name = ?1 AND s.age >= ?2", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(String firstName, Integer age);


    // Il problema delle native query e che se un domani cambiamo database ad es MySql, il nostro query non
    // e piu buona, non funzionera mai
    // Invece la Query con JPQL e buona perche non e specifica a database

    // @Query si aspetta di ritornare qualcosa, non e possibile utilizarla per le delete o per modifiche
    // @Modifying dice a spring che non deve mappare nessuna Entita dal Database, invece
    // stiamo solo modificando dati(no return)
    @Transactional // si usa per le delete e update
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}
