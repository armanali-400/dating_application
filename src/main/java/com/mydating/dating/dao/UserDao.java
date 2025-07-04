package com.mydating.dating.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.mydating.dating.entity.User;
import com.mydating.dating.repository.UserRepository;
import com.mydating.dating.util.User_gender;

@Repository
public class UserDao {
	@Autowired
	UserRepository userRepository;

	public User saveUsers(User user) {
		return userRepository.save(user);
	}

	public List<User> findAllMaleUsers() {
		return userRepository.findByGender(User_gender.MALE);
	}

	public List<User> findAllFemaleUsers() {
		return userRepository.findByGender(User_gender.FEMALE);
	}

	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}

	public List<User> searchByName(String letters) {
		return userRepository.searchByName(letters);
	}

	public List<User> searchByEmail(String emailLetters) {
		return userRepository.searchByEmail(emailLetters);
	}
	
	

}
