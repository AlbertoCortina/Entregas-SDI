package uo.sdi.model;

import java.util.*;
import java.util.HashSet;
import javax.persistence.*;
import uo.sdi.model.types.UserStatus;

@Entity
@Table(name = "TUsers")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login; // Clave primaria (set privado, hay equals y hashCode)
	private String email;
	private String password;
	private boolean isAdmin;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@OneToMany(mappedBy = "user")
	private Set<Task> tasks = new HashSet<Task>();

	@OneToMany(mappedBy = "user")
	private Set<Category> categories = new HashSet<Category>();

	User() {
	}

	public User(String login) {
		setLogin(login);
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	private void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<Task> getTasks() {
		return new HashSet<Task>(tasks);
	}

	protected Set<Task> _getTasks() {
		return tasks;
	}

	public Set<Category> getCategories() {
		return new HashSet<Category>(categories);
	}

	protected Set<Category> _getCategories() {
		return categories;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", email=" + email + ", password="
				+ password + ", status=" + status + ", isAdmin=" + isAdmin
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
}