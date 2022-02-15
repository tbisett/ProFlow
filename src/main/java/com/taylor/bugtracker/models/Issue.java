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
@Table(name="issues")
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Issue name is required")
	@Size(min = 1, max= 50, message= "Issue name must be between 1 and 50 characters")
	private String issueName;
	
	@NotEmpty(message = "Project is required")
//	@Size(min = 1, max= 50, message= "Project must be between 1 and 50 characters")
	private String project;
	
	
	@Size(min = 1, max= 245, message= "Summary must be between 1 and 245 characters")
	private String summary;
	
	
	@Size(min = 1, max= 245, message= "Description must be between 1 and 245 characters")
	private String description;
	
	@NotEmpty(message = "Assignee is required")
//	@Size(min = 1, max= 50, message= "assigne must be between 1 and 50 characters")
	private String assigne;
	
	@NotEmpty(message = "Priority is required")
	private String priority;
	
	
	@Size(min = 1, max= 50, message= "Sprint must be between 1 and 50 characters")
	private String sprint;
	
	//==========ADD TABLE JOINS/RELATIONSHIPS HERE===========
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User issueCreator;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_issues",
			joinColumns = @JoinColumn(name="issue_id"),
			inverseJoinColumns = @JoinColumn(name= "user_id")
			)
	private List<User> issuesUsers;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "issues_projects",
			joinColumns = @JoinColumn(name="issue_id"),
			inverseJoinColumns = @JoinColumn(name= "project_id")
			)
	private List<Project> issuesProjects;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	
	public Issue() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIssueName() {
		return issueName;
	}


	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAssigne() {
		return assigne;
	}


	public void setAssigne(String assigne) {
		this.assigne = assigne;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getSprint() {
		return sprint;
	}


	public void setSprint(String sprint) {
		this.sprint = sprint;
	}


	public User getIssueCreator() {
		return issueCreator;
	}


	public void setIssueCreator(User issueCreator) {
		this.issueCreator = issueCreator;
	}


	public List<User> getIssuesUsers() {
		return issuesUsers;
	}


	public void setIssuesUsers(List<User> issuesUsers) {
		this.issuesUsers = issuesUsers;
	}


	public List<Project> getIssuesProjects() {
		return issuesProjects;
	}


	public void setIssuesProjects(List<Project> issuesProjects) {
		this.issuesProjects = issuesProjects;
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
