package uo.sdi.model;

import java.util.Date;

import javax.persistence.*;

import alb.util.date.DateUtil;

@Entity
@Table(name = "TTasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created = DateUtil.now();

	@Temporal(TemporalType.DATE)
	private Date planned;

	@Temporal(TemporalType.DATE)
	private Date finished;

	@ManyToOne
	private User user;

	@ManyToOne
	private Category category;

	Task() {
	}
	
	public Task(User user) {
		Association.Perform.link(user, this);
	}	

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreated() {
		return created;
	}

	public Date getPlanned() {
		return planned;
	}

	public void setPlanned(Date planned) {
		this.planned = planned;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public User getUser() {
		return user;
	}

	protected void _setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	protected void _setCategory(Category category) {
		this.category = category;
	}
	
	public void desvincularTarea () {
		Association.Perform.unlink(user, this);
		
		if (category != null)
			Association.Classify.unlink(category, this);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", comments=" + comments
				+ ", created=" + created + ", planned=" + planned
				+ ", finished=" + finished + ", user=" + user + ", category="
				+ category + "]";
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
		Task other = (Task) obj;
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