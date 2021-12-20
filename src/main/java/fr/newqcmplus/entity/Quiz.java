package fr.newqcmplus.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@ToString
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NonNull
	@Column(name = "title")
	private String title;

	@NonNull
	@Column(name = "description")
	private String description;

	@NonNull
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "quiz_question", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions;

}
