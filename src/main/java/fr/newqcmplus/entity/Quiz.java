package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "title")
	private String title;

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "description")
	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "quiz_question", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions;

	@Transient
	private boolean available;

}