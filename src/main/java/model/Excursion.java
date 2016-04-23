package model;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity("excursion")
public class Excursion extends Tour implements Serializable {
    
	/**
     * 
     */
    private static final long serialVersionUID = 6958091159172208795L;
    private List<Destination> destinations;


	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

}
