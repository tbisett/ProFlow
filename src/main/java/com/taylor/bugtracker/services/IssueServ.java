package com.taylor.bugtracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taylor.bugtracker.models.Issue;
import com.taylor.bugtracker.repositories.IssueRepo;

@Service
public class IssueServ {
	
	@Autowired
	public IssueRepo issueRepo;
	
	
//	============CREATE AN ISSUE=============
	public Issue createIssue(Issue issue) {
		return issueRepo.save(issue);
	}
	
	
//	=========UPDATE AN ISSUE==========
	public Issue updateIssue(Issue issue) {
		return issueRepo.save(issue);
	}
	
//	=========RETURN A LIST OF ISSUES===========
	public List<Issue> allIssues() {
		return issueRepo.findAll();
	}
	
	
//	==========DELETE AN ISSUE===============
	public void deleteIssue(Long id) {
		issueRepo.deleteById(id);
	}
	
	
//	==========FIND AN ISSUE BY ID=================
//	ALWAYS OPTIONAL RETURN WHEN FINDING BY ID, IF OPTIONALISSUE IS PRESENT IN DB
//	IT RETURNS THAT ISSUE, ELSE RETURNS NULL
	public Issue findIssueById(Long id) {
		Optional<Issue> optionalIssue = issueRepo.findById(id);
		if(optionalIssue.isPresent()) {
			return optionalIssue.get();
		} else {
			return null;
		}
	}
	
	
	
}
