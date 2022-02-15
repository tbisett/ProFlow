package com.taylor.bugtracker.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.taylor.bugtracker.models.LoginUser;
import com.taylor.bugtracker.models.User;
import com.taylor.bugtracker.repositories.UserRepo;

@Service
public class UserServ {
	
	@Autowired
	private UserRepo userRepo;
	
//	========REGISTER USER=============
//	PASS IN USER OBJECT AS NEWUSER SO WE CAN CREATE A NEW INSTANCE OF USER AND BINDING RESULT AS RESULT FOR VALIDATION CHECKS/MESSAGES
//	FIRST IF CHECK IS TO SEE IF EMAIL IS ALREADY IN USE 
//	SECOND IF CHECK IS TO SEE IF THE ENTERED PASSWORD DOES NOT EQUAL THE CONFIRM PASSWORD. IF IT DOESNT MATCH, IT REJECTS THE ENTRY AND SENDS BACK A MESSAGE
//	THIRD IF CHECK IS TO MAKE SURE THE ENTRY HAS NO ERRORS(ERROR REQUIRMENTS SET IN THE USER MODEL)
//	IF ALL IF CHECK ARE PASSED, THE PASSWORD IS HASHED BY BCRYPT AND SET TO THE NEW USER. NEW USER IS THEN SAVED AS A USER OBJECT IN THE DB
	public User registerUser(User newUser, BindingResult result) {
		if (userRepo.findByEmail(newUser.getEmail()).isPresent()) {
			result.rejectValue("email", "Unique", "This email is already in use");
		}
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches", "The confirm password must match password");
		}
		if(result.hasErrors()) {
			return null;
		} else {
			String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(hashed);
			return userRepo.save(newUser);
		}
	}
	
//	=================LOGIN USER===================
//	PASS IN LOGINUSER OBJECT AS NEWLOGIN TO CREATE A NEW INSTANCE OF LOGIN MODEL, BINDING RESULT AS RESULT FOR ERROR CHECKS
//	FIRST IF CHECK IS TO SEE IF THE INFO ENTERED HAS ERRORS(DEFINED IN MODEL) IF IT DOES IT REJECTS THE ENTRY AND RETURNS A MESSAGE
//	PASS IN USER OBJECT AS AN OPTIONAL RETURN AS POTENTIAL USER AND MAKE IT EQUAL FIND USER BY EMAIL AND PASS IN NEWLOGIN.GET EMAIL(COMPARING EMAIL ENTERED TO EMAIL IN DB)
//	SECOND IF CHECK SAYS IF USER EMAIL IS NOT VALID OR PRESENT(IN DB) REJECT AND RETURN NULL
//	PASS IN USER OBJECT AS USER AND MAKE IT EQUAL POTENTIALUSER VARIABLE
//	THIRD IF CHECK IS TO COMPARE THE ENTERED PASSWORD WITH THE PASSWORD IN THE DB, IF THEY DONT MATCH, THE ENTRY IS REJECTED AND RETURNED AS NULL
//  FOURTH IF CHECK IS TO MAKE SURE THE ENTERED PASSWORD PASSES THE VALIDATIONS SET IN THE LOGINUSER MODEL
//	IF ALL IF CHECK ARE PASSED, IT RETURNS USER
	public User login(LoginUser newLogin, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}
		Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
		if (!potentialUser.isPresent()) {
			result.rejectValue("email", "Unique", "Unknown Email");
			return null;
		}
		User user = potentialUser.get();
		if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
			result.rejectValue("password", "Matches", "Invalid Password");
		}
		if (result.hasErrors()) {
			return null;
		} else {
			return user;
		}
		
	}
	
	
//	=============RETURN LIST OF USERS==============
//	RETURNS A LIST OF USERS IN DB USING THE FINDALL FUNCTION FROM USER REPO
	public List<User> allUsers() {
		return userRepo.findAll();
	}
	
	
//	===============UPDATE A USER================
	public User updateUser(User user) {
		return userRepo.save(user);
	}
	
	
//	=============DELETE A USER================
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}
	
//	============FIND A USER BY ID=============
	public User findUserById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
	
}
