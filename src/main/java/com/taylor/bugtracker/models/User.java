package com.taylor.bugtracker.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Name is required")
	@Size(min = 1, max= 50, message= "Name must be between 1 and 50 characters")
	private String name;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Please enter a valid email")
	private String email;
	
	@NotEmpty(message = "Role is required")
	@Size(min = 1, max= 50, message= "Role must be between 1 and 50 characters")
	private String role;
	
	@NotEmpty(message = "Password is required")
	@Size(min = 5, max= 245, message= "Password must be between 5 and 50 characters")
	private String password;
	
	@Transient
	@NotEmpty(message= "Confirm password is required")
	private String confirm;
	
//==========ADD TABLE JOINS/RELATIONSHIPS HERE===========
	@OneToMany(mappedBy="issueCreator", fetch= FetchType.LAZY)
	private List<Issue> issues;
	
	@OneToMany(mappedBy="projectCreator", fetch= FetchType.LAZY)
	private List<Project> projects;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_issues",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name= "issue_id")
			)
	private List<Issue> usersIssues;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_projects",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name= "project_id")
			)
	private List<Project> usersProjects;
	
	
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Issue> getUsersIssues() {
		return usersIssues;
	}

	public void setUsersIssues(List<Issue> usersIssues) {
		this.usersIssues = usersIssues;
	}

	public List<Project> getUsersProjects() {
		return usersProjects;
	}

	public void setUsersProjects(List<Project> usersProjects) {
		this.usersProjects = usersProjects;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate void onUpdate() {
		this.updatedAt = new Date();
	}
	
	
}
