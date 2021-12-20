package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "results")
@Getter
@Setter
@ToString
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    private List<Answer> answers;

    @Column(name = "date_debut")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateDebut;

    @Column(name = "date_fin")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateFin;

}