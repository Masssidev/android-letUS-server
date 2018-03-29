package net.skhu.dto;

import net.skhu.encryption.Encryption;

public class UserDTO {

	Encryption encryption=new Encryption();

	private int id;
	private String user_id;
	private String password;
	private String nickname;
	private String email;
	private int division;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryption.encrypt(password);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", user_id=" + user_id + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", division=" + division + "]";
	}

}
