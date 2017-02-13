package uo.sdi.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "TTasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String comments;

	@Temporal(TemporalType.DATE)
	private Date created;

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

	public Task(String title, Date created) {
		setTitle(title);
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
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

	public void setCreated(Date created) {
		this.created = created;
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

	@Override
	public String toString() {
		return "Task [title=" + title + ", comments=" + comments + ", created="
				+ created + ", planned=" + planned + ", finished=" + finished
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}