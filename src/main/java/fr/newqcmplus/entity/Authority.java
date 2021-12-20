package fr.newqcmplus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name= "authorities")
@Getter
@Setter
public class Authority implements Serializable{

	@Id
	@Column(name = "id")
	private int id;
	
	@NonNull
	@Column(name = "name")
	private String name;
	
}
