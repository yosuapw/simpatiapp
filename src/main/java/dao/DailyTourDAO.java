package dao;

import java.util.List;

import model.DailyTour;

public interface DailyTourDAO {
	List<DailyTour> getAll();

	DailyTour findByLink(String link);
}
