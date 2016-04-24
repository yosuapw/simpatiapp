package model;

public class RoundTripPrice {
	private RoundTripSubPrice half;
	private RoundTripSubPrice full;

	public RoundTripSubPrice getHalf() {
		return half;
	}

	public void setHalf(RoundTripSubPrice half) {
		this.half = half;
	}

	public RoundTripSubPrice getFull() {
		return full;
	}

	public void setFull(RoundTripSubPrice full) {
		this.full = full;
	}

}
