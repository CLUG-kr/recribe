package com.pm717.recribe.beans.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.pm717.recribe.beans.UserDTO;

public interface UserDAO {
	public int isUserEmailDuplicate(String email);
	public int isUserNicknameDuplicate(String nickname);
	public int insertUser(UserDTO user);
	public String findEmailByNickname(String nickname);
	
}
