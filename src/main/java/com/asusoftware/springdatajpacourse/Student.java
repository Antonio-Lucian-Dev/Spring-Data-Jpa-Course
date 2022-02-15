package com.asusoftware.springdatajpacourse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity(name = "Student") // In questo modo chiamiamo la nostra entita come tabella Student. Non serve piu @table
@Table(name = "student", uniqueConstraints = {
        // Ci permette di definire i Constraint, e darli un nome piu specifico
        @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        })
public class Student {

    @Id
    // allocationSize significa di quanto voglio incremetare l'id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    // Come verra generato il valore, generator significa il nome della sequenza da generare, cio quella sopra
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    // Updatable praticamente non permette a nessuno di cambiare la colonna
    @Column(name = "id", updatable = false)
    private Long id;

    // columnDefinition specifica il tipo per la colonna firstName
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    /* Unique rappresenta che il valore deve essere unico
    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email; */

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    // mappedBy forma una bidirectional relantionship
    // dentro il mappedBy fa riferimento alla proprieta student della classe StudentIdCard
    // Quindi quando riceviamo un Student riceviamo anche lo StudentIdCard
    @OneToOne(mappedBy = "student", orphanRemoval = true)
    private StudentIdCard studentIdCard;
}


// orphanRemoval: ci consente di eliminare o no le relationi su una data tabella che stiamo eliminando,
// cioe se eliminiamo Student in questo caso, possiamo decidere se eliminare anche studentIdCard oppure no