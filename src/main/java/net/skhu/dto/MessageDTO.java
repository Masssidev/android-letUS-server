package net.skhu.dto;

public class MessageDTO {
	int id;
	int u_sendId;
	String content;
	String write_date;
	int u_receiveId;
	String nickname;
	int read;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getU_sendId() {
		return u_sendId;
	}
	public void setU_sendId(int u_sendId) {
		this.u_sendId = u_sendId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date.substring(0, 19);
	}
	public int getU_receiveId() {
		return u_receiveId;
	}
	public void setU_receiveId(int u_receiveId) {
		this.u_receiveId = u_receiveId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}

}
