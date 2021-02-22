package com.pm717.recribe.beans.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

public interface UserDAO {
	public int isUserIdDuplicate();
	public int isUserNicknameDuplicate();
	
}
