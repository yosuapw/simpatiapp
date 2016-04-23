package dao;

import java.util.List;

import model.Excursion;

public interface ExcursionDAO {
	List<Excursion> getAll();

	Excursion findByLink(String link);
}
