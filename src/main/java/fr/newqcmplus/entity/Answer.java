package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name= "answers")
@Getter
@Setter
@ToString
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "response")
    private boolean response;

}