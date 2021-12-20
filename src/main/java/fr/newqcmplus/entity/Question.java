package fr.newqcmplus.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private List<Item> items;

}