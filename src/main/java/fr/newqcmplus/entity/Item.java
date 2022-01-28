package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "title")
	private String title;

	@Column(name = "response")
	private boolean response;

}
