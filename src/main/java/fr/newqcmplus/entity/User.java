package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "firstname")
	private String firstname;

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "lastname")
	private String lastname;

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "company")
	private String company;

	@NotBlank(message = "{input.not.blank}")
	@Size(max = 255, message = "{input.max.255}")
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Transient
	private String passwordConfirmation;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

	@Column(name = "enabled")
	private boolean enabled;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id;
	}

	public String getFullName() {
		return firstname + " " + lastname;
	}

	public String getAvatarName() {
		return firstname.substring(0, 1).toUpperCase() + lastname.substring(0, 1).toUpperCase();
	}

	public String getAuthoritiesName() {
		String res = "";
		for (Authority authority : authorities) {
			res += authority.getName().toLowerCase() + ", ";
		}
		return res.length() >= 2 ? res.substring(0, res.length() - 2) : res;
	}

	public boolean hasAuthority(String authorityName) {
		for (Authority authority : authorities) {
			if (authority.getName().equals(authorityName)) return true;
		}
		return false;
	}
	
}
