package model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;

@Entity("explorer")
public class Explorer extends Tour  implements Serializable {

    /**
     * 
     */
	private static final long serialVersionUID = -619807945143741352L;
	private String information;
	private String line;
	private String whatToBring;
	private String tourIncluded;
	private Price prices;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getWhatToBring() {
		return whatToBring;
	}

	public void setWhatToBring(String whatToBring) {
		this.whatToBring = whatToBring;
	}

	public String getTourIncluded() {
		return tourIncluded;
	}

	public void setTourIncluded(String tourIncluded) {
		this.tourIncluded = tourIncluded;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Price getPrices() {
		return prices;
	}

	public void setPrices(Price prices) {
		this.prices = prices;
	}

}
