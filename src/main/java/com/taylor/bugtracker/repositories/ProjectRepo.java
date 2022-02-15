package com.taylor.bugtracker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taylor.bugtracker.models.Project;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Long> {
	
	List<Project> findAll();
	
//	Optional<Project> findById();
}
