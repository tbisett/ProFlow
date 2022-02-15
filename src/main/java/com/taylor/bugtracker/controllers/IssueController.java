package com.taylor.bugtracker.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.taylor.bugtracker.models.Issue;
import com.taylor.bugtracker.models.Project;
import com.taylor.bugtracker.models.User;
import com.taylor.bugtracker.services.IssueServ;
import com.taylor.bugtracker.services.ProjectServ;
import com.taylor.bugtracker.services.UserServ;
import com.taylor.bugtracker.validator.UserValidator;

@Controller
public class IssueController {
	
	@Autowired
	public UserServ userServ;
	
	@Autowired
	public IssueServ issueServ;
	
	@Autowired 
	public ProjectServ projectServ;
	
	@Autowired
	public UserValidator userValidator;
	
	@Autowired
	public UserController userController;
	
	
//	=============CREATE ISSUE PAGE(GET REQUEST)================
	@GetMapping("/issues")
	public String newIssue(@ModelAttribute("newIssue") Issue newIssue, Model model, HttpSession session) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}else {
			Long userId = (Long) session.getAttribute("user_id");
			User u = userServ.findUserById(userId);
			List<User> users = userServ.allUsers();
			List<Project> projects = projectServ.allProjects();
			model.addAttribute("user", u);
			model.addAttribute("users", users);
			model.addAttribute("projects", projects);
			return "issue.jsp";
		}
		
	}
	
//	===============CREATE ISSUE POST===============
	@PostMapping("/issues")
	public String createIssue(@Valid @ModelAttribute("newIssue") Issue newIssue, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			List<User> users = userServ.allUsers();
			List<Project> projects = projectServ.allProjects();
			model.addAttribute("users", users);
			model.addAttribute("projects", projects);
			return "issue.jsp";
		} else {
			issueServ.createIssue(newIssue);
			return "redirect:/dashboard";
		}
	}
	
//	==========EDIT ISSUE PAGE=============
	@GetMapping("/editissue/{id}")
	public String editIssuePage(@PathVariable("id") Long id, @ModelAttribute("editIssue") Issue issue, Model model) {
		Issue editIssue = issueServ.findIssueById(id);
		List<User> users = userServ.allUsers();
		List<Project> projects = projectServ.allProjects();
		model.addAttribute("users", users);
		model.addAttribute("projects", projects);
		model.addAttribute("editIssue", editIssue);
		return "editIssue.jsp";
	}
	
//	============EDIT ISSUE PUT=============
	@PutMapping("/editissue/{id}") 
	public String editIssuePut(@Valid @ModelAttribute("editIssue") Issue issue, BindingResult result) {
		if(result.hasErrors()) {
			return "editIssue.jsp";
		} else {
			issueServ.updateIssue(issue);
			return "redirect:/dashboard";
		}
	}
	
//	===========DELETE AN ISSUE=================
	@GetMapping("/issue/delete/{id}")
	public String deleteIssue(@PathVariable("id") Long id) {
		issueServ.deleteIssue(id);
		return "redirect:/dashboard";
	}
			
	
	
}
