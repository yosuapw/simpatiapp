package model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity("dailyTour")
public class DailyTour {
    
    private Price prices;

	private List<Destination> destinations;

    public Price getPrices() {
        return prices;
    }

    public void setPrices(Price prices) {
        this.prices = prices;
    }

	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}
    
}
