package model;

import org.mongodb.morphia.annotations.Entity;

@Entity("roundtrip")
public class RoundTrip extends Tour {
	private DailyTourPrice half;
	private DailyTourPrice full;
}
