package com.taylor.bugtracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taylor.bugtracker.models.Project;
import com.taylor.bugtracker.repositories.ProjectRepo;

@Service
public class ProjectServ {
	
	@Autowired
	private ProjectRepo projectRepo;
	
//	============CREATE A PROJECT=============
	public Project createProject(Project project) {
		return projectRepo.save(project);
	}
	
	
//	=========UPDATE A PROJECT==========
	public Project updateProject(Project project) {
		return projectRepo.save(project);
	}
	
//	=========RETURN A LIST OF PROJECTS===========
	public List<Project> allProjects() {
		return projectRepo.findAll();
	}
	
	
//	==========DELETE A PROJECT===============
	public void deleteProject(Long id) {
		projectRepo.deleteById(id);
	}
	
	
//	==========FIND A PROJECT BY ID=================
//	ALWAYS OPTIONAL RETURN WHEN FINDING BY ID, IF OPTIONALPROJECT IS PRESENT IN DB
//	IT RETURNS THAT PROJECT, ELSE RETURNS NULL
	public Project findProjectById(Long id) {
		Optional<Project> optionalProject = projectRepo.findById(id);
		if(optionalProject.isPresent()) {
			return optionalProject.get();
		} else {
			return null;
		}
	}
	
	
}
