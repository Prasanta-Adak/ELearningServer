package com.elearningserver.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elearningserver.helper.UserFoundException;
import com.elearningserver.model.Role;
import com.elearningserver.model.User;
import com.elearningserver.model.UserRole;
import com.elearningserver.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//creating normal user
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		user.setProfile("default.png");
		
		//encoding password
		
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		
		Set<UserRole> roles=new HashSet();
		
		Role role=new Role();
		role.setRoleId(45L);
		role.setRoleName("User");
		
		UserRole userRole=new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		
		roles.add(userRole);
		
		return this.userService.createUser(user, roles);
	}
	
	//create Professor
	@PostMapping("/createProfessor")
	public User createProfessor(@RequestBody User user) throws Exception {
		
		user.setProfile("default.png");
		
		//encoding password
		
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		
		Set<UserRole> roles=new HashSet();
		
		Role role=new Role();
		role.setRoleId(46L);
		role.setRoleName("Professor");
		
		UserRole userRole=new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		
		roles.add(userRole);
		
		return this.userService.createUser(user, roles);
	}
	
	
	//get user by username
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}
	
	//get all normal user
		@GetMapping("/getAllUsers")
		public List<User> getAllUsers() {
			return this.userService.getAllUsers();
		}
		
		       //get all professor
				@GetMapping("/getAllProfessors")
				public List<User> getAllProfessors() {
					return this.userService.getAllProfessors();
				}
	
	//delete user by id
	@GetMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){
		this.userService.deleteUser(userId);
		return ResponseEntity.ok("Successfully Deleted");
	}
	
	
	//update user
	//...
	
	@ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok("user already there");
    }
}
