package com.mydating.dating.dto;

import java.util.List;

import com.mydating.dating.util.User_gender;
import lombok.Data;
@Data
public class MatchingUser{
	private int id;
	private String name;
	private String email;
	private long phone;
	private int age;
	private User_gender gender;
	private List<String> interests;
	private int ageDiff;
	private int mic;
}