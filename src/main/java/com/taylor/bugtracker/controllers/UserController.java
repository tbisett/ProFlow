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
import org.springframework.web.bind.annotation.PostMapping;

import com.taylor.bugtracker.models.Issue;
import com.taylor.bugtracker.models.LoginUser;
import com.taylor.bugtracker.models.Project;
import com.taylor.bugtracker.models.User;
import com.taylor.bugtracker.services.IssueServ;
import com.taylor.bugtracker.services.ProjectServ;
import com.taylor.bugtracker.services.UserServ;
import com.taylor.bugtracker.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	public UserServ userServ;
	
	@Autowired
	public IssueServ issueServ;
	
	@Autowired 
	public ProjectServ projectServ;
	
	@Autowired
	public UserValidator userValidator;
	
	@Autowired 
	public IssueController issueController;
	
	
//	=========INDEX PAGE(LOGIN/REG)(GET REQUEST)============
	@GetMapping("/")
	public String loginReg(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("loginUser", new LoginUser());
		return "index.jsp";
	}
	
	
//	======REGISTER PAGE(GET REQUEST)===========
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("newUser", new User());
		return "register.jsp";
	}
	
//	===========REGISTER(POST MAPPING)================
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {
		userValidator.validate(newUser, result);
		userServ.registerUser(newUser, result);
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		} else {
			session .setAttribute("user_id", newUser.getId());
			return "redirect:/dashboard";
		}
		
	}
	
//	=============LOGIN(POST MAPPING)=================
	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("loginUser") LoginUser newLogin,
			BindingResult result, HttpSession session, Model model) {
		User user = userServ.login(newLogin, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", user.getId());
			return "redirect:/dashboard";
		}
	}
	
	
//	==============USER LOGOUT=============
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
//	============DASHBOARD PAGE(GET REQUEST)==============
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		} else {
			Long userId = (Long) session.getAttribute("user_id");
			User u = userServ.findUserById(userId);
			List<User> users = userServ.allUsers();
			List<Issue> issues = issueServ.allIssues();
			List<Project> projects = projectServ.allProjects();
			int issueCount = 0;
			int projectCount = 0;
			for(Issue i: issues) {
				issueCount++;
			}
			for(Project p: projects) {
				projectCount++;
			}
			model.addAttribute("user", u);
			model.addAttribute("users", users);
			model.addAttribute("issues", issues);
			model.addAttribute("projects", projects);
			model.addAttribute("totalIssues", issueCount);
			model.addAttribute("totalProjects", projectCount);
			return "dashboard.jsp";
		}
	}
	
	
	
}
