package model;

import java.io.Serializable;
import java.util.List;

public class Destination implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -3714698254335976308L;
    private Integer index;
	private String title;
	private String description;
	private String time;
	private List<String> descriptions;
	private List<String> additionalInfos;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(List<String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
