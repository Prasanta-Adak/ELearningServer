package com.elearningserver.service.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.elearningserver.helper.UserFoundException;
import com.elearningserver.model.User;
import com.elearningserver.model.UserRole;
import com.elearningserver.repository.RoleRepository;
import com.elearningserver.repository.UserRepository;
import com.elearningserver.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	//creating user
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local=this.userRepository.findByUsername(user.getUsername());
		
		if(local!=null) {
			System.out.println("User is already there");
//			throw new Exception("User Already present !!");
			throw new UserFoundException();
		}else {
			//create user
			for(UserRole ur:userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRole().addAll(userRoles);
			local=this.userRepository.save(user);
		}
		return local;
	}


	//get user by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}

// get all normal user 
	@Override
	public List<User> getAllUsers(){
		List<User> findAllp = this.userRepository.findAll();
		
		List<User> patients=new ArrayList<>();
		for(User user : findAllp) {
			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
			for (GrantedAuthority auth : authorities) {
				if(auth.getAuthority().equals("User")) {
					patients.add(user);
				}
			}
		}
		return patients;
	}

	
	// get all professors 
		@Override
		public List<User> getAllProfessors(){
			List<User> findAllp = this.userRepository.findAll();
			
			List<User> doctors=new ArrayList<>();
			for(User user : findAllp) {
				Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
				for (GrantedAuthority auth : authorities) {
					if(auth.getAuthority().equals("Professor")) {
						doctors.add(user);
					}
				}
			}
			return doctors;
		}
		
	//delete user by id
	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);		
	}

}
