package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name= "authorities")
@Getter
@Setter
public class Authority implements Serializable{

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;
	
}
