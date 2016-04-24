package dao;

import java.util.List;

import model.RoundTrip;

public interface RoundTripDAO {
	List<RoundTrip> getAll();

	RoundTrip findByLink(String link);
}
