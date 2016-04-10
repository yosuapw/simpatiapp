package model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity("dailyTour")
public class DailyTour extends Tour {
    
	private List<Destination> destinations;


	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

}
