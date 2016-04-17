package model;

import java.io.Serializable;

public class Explorer extends Tour  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6416426211748293483L;

	/**
	 * 
	 */
	private static final long serialVersionUID = -619807945143741352L;
	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}
