package com.asusoftware.springdatajpacourse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
// Il name sulla @Entity ci permette di usarlo quando facciamo le @Query
@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        // Ci permette di definire i Constraint, e darli un nome piu specifico
        @UniqueConstraint(name = "student_id_card_number_unique", columnNames = "card_number")
})
public class StudentIdCard {

    @Id
    // allocationSize significa di quanto voglio incremetare l'id
    @SequenceGenerator(name = "student_id_sequence", sequenceName = "student_id_sequence", allocationSize = 1)
    // Come verra generato il valore, generator significa il nome della sequenza da generare, cio quella sopra
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_sequence")
    // Updatable praticamente non permette a nessuno di cambiare la colonna
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;

    // cascade specifica le azioni che si succedono
    // Propaga tutte le operazioni..Insert, update, delete etc..
    // DETACH: rimuove anche il child dal contesto persistente
    // MERGE: Copia l'entita al contesto persisitito
    // PERSIST: e per quando salviamo l'entita
    // REFRESH: quando returniamo l'entita dal database
    // REMOVE: quando rimuoviamo l'entita dal database
    // FetchType ci permette di definire nelle relazioni tra tabelle se mostrare anche la relazione con l'altra
    // tabella(EAGER: ritorna anche l'altra tabella alla get di questa tabella) oppure no(LAZY)
    // Di default nelle relazioni @One to One il fetchType e EAGER
    // Mentre per le altre relazioni: @One to many e @Many to many di default fetchType e LAZY
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // referencedColumnName si riferisce all'id della classe Student
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
}


// Uni vs BiDirectional Relationship
// Unidirectional: relazione in un unico senso. Cioe questa tabella mappa anche la tabella Student
// ma la tabella Student non mappa la tabella StudentIdCard cioe questa tabella
// BiDirectional: