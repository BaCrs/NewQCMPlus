package fr.newqcmplus.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name= "users")
@Getter
@Setter
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NonNull
	@Column(name = "firstname")
	private String firstname;
	
	@NonNull
	@Column(name = "lastname")
	private String lastname;
	
	@NonNull
	@Column(name = "company")
	private String company;
	
	@NonNull
	@Column(name = "username")
	private String username;
	
	@NonNull
	@Column(name = "password")
	private String password;

	@Transient
	private String passwordConfirmation;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

	@Column(name = "enabled")
	private boolean enabled;

	public String getFullname() {
		return firstname + " " + lastname;
	}
	public String getInitial() {
		return firstname.substring(0, 1).toUpperCase() + lastname.substring(0, 1).toUpperCase();
	}

	public String getListOfAuthorities() {
		String res = "";
		for (Authority authority : authorities) {
			res += authority.getName() + ", ";
		}
		return res.length() >= 2 ? res.substring(0, res.length() - 2) : res;
	}
	
}
