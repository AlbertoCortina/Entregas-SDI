package uo.sdi.model;

import java.util.*;

import javax.persistence.*;

import alb.util.date.DateUtil;

@Entity
@Table(name = "TCategories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = DateUtil.now();

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
	
	public Date getCreated() {
		return created;
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
		return "Category [id=" + id + ", name=" + name + ", created=" + created
				+ ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}