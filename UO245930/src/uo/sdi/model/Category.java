package uo.sdi.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "TCategories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "category")
	private Set<Task> tasks = new HashSet<Task>();

	Category() {
	}

	public Category(String name) {
		setName(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	protected void _setUser(User user) {
		this.user = user;
	}

	public Set<Task> getTasks() {
		return new HashSet<Task>(tasks);
	}

	protected Set<Task> _getTasks() {
		return tasks;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}