package com.pm717.recribe.beans;

public class UserDTO {
    private String user_uid;
    private String user_name;
    private String user_nickname;
    private String user_password;
    private String user_email;
    private int user_gender;
    private int user_age;
    private String user_date;
     
	public UserDTO() {
		super();
	}

	public UserDTO(String user_uid, String user_name, String user_nickname, String user_password, String user_email,
			int user_gender, int user_age, String user_date) {
		super();
		this.user_uid = user_uid;
		this.user_name = user_name;
		this.user_nickname = user_nickname;
		this.user_password = user_password;
		this.user_email = user_email;
		this.user_gender = user_gender;
		this.user_age = user_age;
		this.user_date = user_date;
	}

	public String getUser_uid() {
		return user_uid;
	}

	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(int user_gender) {
		this.user_gender = user_gender;
	}

	public int getUser_age() {
		return user_age;
	}

	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}

	public String getUser_date() {
		return user_date;
	}

	public void setUser_date(String user_date) {
		this.user_date = user_date;
	}

}
