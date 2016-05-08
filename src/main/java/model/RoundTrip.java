package model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity("roundtrip")
public class RoundTrip extends Tour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4252619672881315902L;
	private List<String> additionalInfos;
	private List<Destination> destinations;

	public List<String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(List<String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

}
