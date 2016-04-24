package model;

public class RoundTripSubPrice {

	//
	// "2": 218,
	// "3-5": 186,
	// "6-9": 156,
	// "10-15": 136,
	// "single": 68

	private Integer two;
	private Integer threeToFive;
	private Integer sixToNine;
	private Integer tenToFifteen;
	private Integer single;

	public Integer getTwo() {
		return two;
	}

	public void setTwo(Integer two) {
		this.two = two;
	}

	public Integer getThreeToFive() {
		return threeToFive;
	}

	public void setThreeToFive(Integer threeToFive) {
		this.threeToFive = threeToFive;
	}

	public Integer getSixToNine() {
		return sixToNine;
	}

	public void setSixToNine(Integer sixToNine) {
		this.sixToNine = sixToNine;
	}

	public Integer getTenToFifteen() {
		return tenToFifteen;
	}

	public void setTenToFifteen(Integer tenToFifteen) {
		this.tenToFifteen = tenToFifteen;
	}

	public Integer getSingle() {
		return single;
	}

	public void setSingle(Integer single) {
		this.single = single;
	}
}
