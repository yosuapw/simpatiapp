package model;

import net.binggl.ninja.mongodb.MorphiaModel;

public class Tour extends MorphiaModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 723189505453315712L;
	private String link;
	private String type;
	private String headTitle;
	private String image;
	private String additionalInfo;

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


}
