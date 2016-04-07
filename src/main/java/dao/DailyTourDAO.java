package dao;

import java.util.List;

import model.DailyTour;

public interface DailyTourDAO {
    long countAll();

	List<DailyTour> getAll();
}
