package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "{input.not.blank}")
    @Size(max = 255, message = "{input.max.255}")
    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private List<Item> items;

}