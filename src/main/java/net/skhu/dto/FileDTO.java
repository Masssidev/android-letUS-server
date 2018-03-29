package net.skhu.dto;

public class FileDTO {

	private int id;
	private String path;
	private int priv;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getPrivate() {
		return priv;
	}
	public void setPrivate(int priv) {
		this.priv = priv;
	}

}
