package com.taylor.bugtracker.controllers;

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

import com.taylor.bugtracker.models.Project;
import com.taylor.bugtracker.models.User;
import com.taylor.bugtracker.services.IssueServ;
import com.taylor.bugtracker.services.ProjectServ;
import com.taylor.bugtracker.services.UserServ;
import com.taylor.bugtracker.validator.UserValidator;

@Controller
public class ProjectController {
	
	@Autowired
	public UserServ userServ;
	
	@Autowired
	public IssueServ issueServ;
	
	@Autowired 
	public ProjectServ projectServ;
	
	@Autowired
	public UserValidator userValidator;
	
	
//	=============CREATE PROJECT PAGE(GET REQUEST)================
	@GetMapping("/newproject")
	public String newProject(@ModelAttribute("newProject") Project newProject, Model model, HttpSession session) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}else {
			Long userId = (Long) session.getAttribute("user_id");
			User u = userServ.findUserById(userId);
			model.addAttribute("user", u);
			return "newProject.jsp";
		}
		
	}
			
//	============CREATE PROJECT POST============
	@PostMapping("/newproject")
	public String createProject(@Valid @ModelAttribute("newProject") Project newProject, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "newProject.jsp";
		} else {
			projectServ.createProject(newProject);
			return "redirect:/dashboard";
		}
	}
	
//	========================EDIT A PROJECT PAGE=================
	@GetMapping("/editproject/{id}")
	public String editProjectPage(@PathVariable("id") Long id, @ModelAttribute("editProject") Project project, Model model, HttpSession session) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}else {
		Project editProject = projectServ.findProjectById(id);
		Long userId = (Long) session.getAttribute("user_id");
		User u = userServ.findUserById(userId);
		model.addAttribute("editProject", editProject);
		model.addAttribute("user", u);
		return "editProject.jsp";
		}
	}
	
//	=================EDIT PROJECT PUT=================
	@PutMapping("/editproject/{id}")
	public String editProjectPut(@Valid @ModelAttribute("editProject") Project editProject, BindingResult result) {
		if(result.hasErrors()) {
			return "editProject.jsp";
		} else {
			projectServ.updateProject(editProject);
			return "redirect:/dashboard";
		}
	}
	
//	==================DELETE A PROJECT=================
	@GetMapping("/project/delete/{id}")
	public String deleteProject(@PathVariable("id") Long id) {
		projectServ.deleteProject(id);
		return "redirect:/dashboard";
	}
			
	
}
