package controllers;

import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.DailyTourDAO;
import dao.ExplorerDAO;

@Singleton
public class TourController {

	DailyTourDAO dailyTourDAO;

	ExplorerDAO explorerDAO;

	@Inject
	public TourController(DailyTourDAO dailyTourDAO, ExplorerDAO explorerDAO) {
		this.dailyTourDAO = dailyTourDAO;
		this.explorerDAO = explorerDAO;
	}

	public Result tour(@PathParam("id") String id) {
		Result result = Results.html();
		if (id.equalsIgnoreCase("dailytour")) {
			result.render("tours", dailyTourDAO.getAll());
		} else if (id.equalsIgnoreCase("explorers")) {
			result.render("tours", explorerDAO.getAll());
		} else {
			result.render("tours", dailyTourDAO.getAll());
		}
		return result;
	}

	public Result explorer() {
		Result result = Results.html();
		result.render("tours", explorerDAO.getAll());
		return result;
	}
}
