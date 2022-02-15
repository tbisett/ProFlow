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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Project name is required")
	@Size(min = 1, max= 50, message= "Issue name must be between 1 and 50 characters")
	private String projectName;
	
	@NotEmpty(message = "Project name is required")
	@Size(min = 1, max= 245, message= "Issue name must be between 1 and 245 characters")
	private String goal;
	
	@NotEmpty(message = "Features is required")
	@Size(min = 1, max= 245, message= "Features must be between 1 and 245 characters")
	private String features;
	
	@NotEmpty(message = "Technologies is required")
	@Size(min = 1, max= 145, message= "Technologies must be between 1 and 145 characters")
	private String technologies;
	
	//==========ADD TABLE JOINS/RELATIONSHIPS HERE===========
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User projectCreator;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_projects",
			joinColumns = @JoinColumn(name="project_id"),
			inverseJoinColumns = @JoinColumn(name= "user_id")
			)
	private List<User> projectsUsers;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "issues_projects",
			joinColumns = @JoinColumn(name="project_id"),
			inverseJoinColumns = @JoinColumn(name= "issue_id")
			)
	private List<Issue> projectsIssues;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	
	public Project() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getGoal() {
		return goal;
	}


	public void setGoal(String goal) {
		this.goal = goal;
	}


	public String getFeatures() {
		return features;
	}


	public void setFeatures(String features) {
		this.features = features;
	}


	public String getTechnologies() {
		return technologies;
	}


	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}


	public User getProjectCreator() {
		return projectCreator;
	}


	public void setProjectCreator(User projectCreator) {
		this.projectCreator = projectCreator;
	}


	public List<User> getProjectsUsers() {
		return projectsUsers;
	}


	public void setProjectsUsers(List<User> projectsUsers) {
		this.projectsUsers = projectsUsers;
	}


	public List<Issue> getProjectsIssues() {
		return projectsIssues;
	}


	public void setProjectsIssues(List<Issue> projectsIssues) {
		this.projectsIssues = projectsIssues;
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
