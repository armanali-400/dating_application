package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mydating.dating.dao.UserDao;
import com.mydating.dating.dto.MatchingUser;
import com.mydating.dating.entity.User;
import com.mydating.dating.util.UserSorting;
import com.mydating.dating.util.User_gender;

@Service
public class UserService {

	@Autowired
	UserDao userDao;


	public ResponseEntity<?> saveUsers(User user) {
		User savedUser = userDao.saveUsers(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	public ResponseEntity<?> findAllMaleUsers() {
		List<User> maleUsers = userDao.findAllMaleUsers();
		if(maleUsers.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No male user found");
		return ResponseEntity.status(HttpStatus.OK).body(maleUsers);
	}

	public ResponseEntity<?> findAllFemaleUsers() {
		List<User> femaleUsers = userDao.findAllFemaleUsers();
		if(femaleUsers.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No female users found");
		return ResponseEntity.status(HttpStatus.OK).body(femaleUsers);
	}

	public ResponseEntity<?> findBestMatch(int id, int top) {
		Optional<User> optional = userDao.findUserById(id);
		if(optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid User id");
		
		User user = optional.get();
		
		List<User> users = null;
		
		if(user.getGender().equals(User_gender.MALE)) {
			users = userDao.findAllFemaleUsers();
		}
		else {
			users= userDao.findAllMaleUsers();
		}
		
		List<MatchingUser> matchingUsers = new ArrayList<>();
		
		for(User u: users) {
			MatchingUser mu = new MatchingUser();
			mu.setId(u.getId());
			mu.setName(u.getName());
			mu.setEmail(u.getEmail());
			mu.setPhone(u.getPhone());
			mu.setAge(u.getAge());
			mu.setInterests(u.getInterests());
			mu.setGender(u.getGender());
			
			mu.setAgeDiff(Math.abs(user.getAge()-u.getAge()));
			
			List<String> interest1 = user.getInterests();
			List<String> interest2 = u.getInterests();
			
			int mic = 0;
			
			for(String s : interest1) {
				if(interest2.contains(s)) {
					mic++;
				}
			}
			mu.setMic(mic);
			matchingUsers.add(mu);
			
		}
		
		Comparator<MatchingUser> c = new UserSorting();
		
		Collections.sort(matchingUsers,c);
		 
		List<MatchingUser> result = new ArrayList<>();
		
		for(MatchingUser mu: matchingUsers) {
			if(top == 0) {
				break;
			}
			else {
				result.add(mu);
				top--;
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	public ResponseEntity<?> searchByName(String letters) {
		List<User> users = userDao.searchByName("%"+letters+"%");
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found with letters"+letters);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	public ResponseEntity<?> searchByEmail(String emailLetters) {
		List<User> users = userDao.searchByEmail("%"+emailLetters+"%");
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found with the letters "+ emailLetters +" in email");
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	

}
