package model;

import java.io.Serializable;

public class Payment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String link;

	private String status;

	public Payment(String link, String status) {
		super();
		this.link = link;
		this.status = status;
	}

	public Payment() {
		super();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
