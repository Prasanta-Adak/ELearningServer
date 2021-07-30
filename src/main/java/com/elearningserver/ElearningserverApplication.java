package com.elearningserver;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.elearningserver.helper.UserFoundException;
import com.elearningserver.model.Role;
import com.elearningserver.model.User;
import com.elearningserver.model.UserRole;
import com.elearningserver.service.UserService;

@SpringBootApplication
public class ElearningserverApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ElearningserverApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting code....");
		
		try {
		User user=new User();
		user.setFirstName("Prasanta");
		user.setLastName("Adak");
		user.setUsername("prasanta123");
		user.setPassword(this.bCryptPasswordEncoder.encode("123"));
		user.setEmail("abc@gmail.com");
		user.setPhone("4578968457");
		user.setProfile("mypic.png");
		
		Role role1=new Role();
		role1.setRoleId(44L);
		role1.setRoleName("Admin");
		
		Set<UserRole> userRoleSet=new HashSet<>();
		UserRole userRole=new UserRole();
		userRole.setRole(role1);
		userRole.setUser(user);
		
		userRoleSet.add(userRole);
		
		User user1 = this.userService.createUser(user, userRoleSet);
		}catch(UserFoundException e) {
			e.printStackTrace();
		}
	}

}
