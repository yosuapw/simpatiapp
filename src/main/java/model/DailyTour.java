package model;

import org.mongodb.morphia.annotations.Entity;

@Entity("dailyTour")
public class DailyTour {
    
    private Price prices;

    public Price getPrices() {
        return prices;
    }

    public void setPrices(Price prices) {
        this.prices = prices;
    }
    
    
}
