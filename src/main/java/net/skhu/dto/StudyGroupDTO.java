package net.skhu.dto;

public class StudyGroupDTO {
	private int id;
	private int study_group_id;
	private int study_user_id;
	private String title;
	private String content;
	private int f_id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudy_group_id() {
		return study_group_id;
	}
	public void setStudy_group_id(int study_group_id) {
		this.study_group_id = study_group_id;
	}
	public int getStudy_user_id() {
		return study_user_id;
	}
	public void setStudy_user_id(int study_user_id) {
		this.study_user_id = study_user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

}
