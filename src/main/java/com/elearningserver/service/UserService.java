package com.elearningserver.service;

import java.util.List;
import java.util.Set;

import com.elearningserver.model.User;
import com.elearningserver.model.UserRole;

public interface UserService {

	//creating User
	public User createUser(User user,Set<UserRole> userRoles) throws Exception;

	//get user by username
	public User getUser(String username);
	
	//delete user by id
	public void deleteUser(Long userId);
	
	//get all normal user
	public List<User> getAllUsers();
	
	//get all professor
	public List<User> getAllProfessors();
	
}
