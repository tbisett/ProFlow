package com.taylor.bugtracker.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taylor.bugtracker.models.Issue;

@Repository
public interface IssueRepo extends CrudRepository<Issue, Long> {
	
	List<Issue> findAll();
	
//	Optional<Issue> findById();
}
