package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	// ** FOR PAGES REQUESTS **

	public Result tour(@PathParam("id") String id) {
		Result result = Results.html();
		result.render("state", id);
		if (id.equalsIgnoreCase("all")) {
			result.render("tours", getAllTours());
		} else if (id.equalsIgnoreCase("dailytour")) {
			result.render("tours", dailyTourDAO.getAll());
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tours", explorerDAO.getAll());
		} else {
			result.render("tours", dailyTourDAO.getAll());
		}
		return result;
	}

	private List<Object> getAllTours() {

		List<Object> lstObject = new ArrayList<Object>();
		lstObject.addAll(dailyTourDAO.getAll());
		lstObject.addAll(explorerDAO.getAll());
		Collections.shuffle(lstObject);

		return lstObject;
	}

	public Result explorer() {
		Result result = Results.html();
		result.render("tours", explorerDAO.getAll());
		return result;
	}

	public Result detail(@PathParam("id") String id,
			@PathParam("link") String link) {
		Result result = Results.html();

		if (id.equalsIgnoreCase("dailytour")) {
			result.render("tour", dailyTourDAO.findByLink(link));
		} else {
			result.render("tour", explorerDAO.findByLink(link));
		}
		return result;
	}

	// ** FOR JSON REQUESTS **
	public Result getAll() {
		return Results.json().render(getAllTours());
	}

	public Result findByLink(@PathParam("id") String id,
			@PathParam("link") String link) {

		if (id.equalsIgnoreCase("dailytour")) {
			return Results.json().render(dailyTourDAO.findByLink(link));
		} else {
			return Results.json().render(explorerDAO.findByLink(link));
		}
	}
}
