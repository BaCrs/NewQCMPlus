package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    private List<Answer> answers;

    @Column(name = "date_debut")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date start;

    @Column(name = "date_fin")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date end;

    public String getCompletionDate() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(start);
    }

    public String getTimeSpent() {
        long diff = end.getTime() - start.getTime();
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS) + "s";
    }

    public String getScore() {
        int score = 0;
        for (Question question : quiz.getQuestions()) {
            boolean isCorrect = true;
            for (Item item : question.getItems()) {
                Answer answer = this.findAnswerByItem(item);
                if (answer.isResponse() != item.isResponse()) {
                    isCorrect = false;
                    break;
                }
            }
            if (isCorrect) score++;
        }
        return score + "/" + quiz.getQuestions().size();
    }

    private Answer findAnswerByItem(Item item) {
        for (Answer answer : answers) {
            if (answer.getItem() == item) return answer;
        }
        return null;
    }

}