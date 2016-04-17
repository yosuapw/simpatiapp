package model;

import java.io.Serializable;

public class Price  implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8394258322963816435L;

    private Integer adult;
    
    private Integer children;
    
	private Integer car;

	private String additionalInfo;

    public Integer getAdult() {
        return adult;
    }
    public void setAdult(Integer adult) {
        this.adult = adult;
    }
    public Integer getChildren() {
        return children;
    }
    public void setChildren(Integer children) {
        this.children = children;
    }

	public Integer getCar() {
		return car;
	}

	public void setCar(Integer car) {
		this.car = car;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
    
}
