package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.IUserRepository;
import com.example.repository.impl.UserRepositoryImpl;
import com.example.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	IUserRepository userRepo = new UserRepositoryImpl();
	
	@Override
	public List<User> findAllUsers() {
		return userRepo.findAllUsers();
	}
	
	public static void main(String[] args) {
		//test
		IUserService userService = new UserServiceImpl();
		System.out.println(userService.findAllUsers());
	}
}
