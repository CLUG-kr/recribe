package com.pm717.recribe.beans.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.pm717.recribe.beans.UserDTO;

public interface UserDAO {
	public int isUserIdDuplicate();
	public int isUserNicknameDuplicate();
	public int insertUser(UserDTO user);
	
}
