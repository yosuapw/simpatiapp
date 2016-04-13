package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.DailyTour;
import model.Explorer;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.PathParam;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.DailyTourDAO;
import dao.ExplorerDAO;

@Singleton
public class TourController {

	DailyTourDAO dailyTourDAO;

	ExplorerDAO explorerDAO;
	
	NinjaCache ninjaCache;

	@Inject
	public TourController(DailyTourDAO dailyTourDAO, ExplorerDAO explorerDAO, NinjaCache ninjaCache) {
		this.dailyTourDAO = dailyTourDAO;
		this.explorerDAO = explorerDAO;
		this.ninjaCache = ninjaCache;
	}

	public Result tour(@PathParam("id") String id) {
		Result result = Results.html();
		List<DailyTour> dailyTours = ninjaCache.get("dailyTours", List.class);
		List<Explorer> explorers = ninjaCache.get("explorers", List.class);
		result.render("state", id);
		if (id.equalsIgnoreCase("all")) {
			List<Object> lstObject = new ArrayList<Object>();
			
			if (dailyTours == null) dailyTours = dailyTourDAO.getAll();
			lstObject.addAll(dailyTours);
			
			if (explorers == null) explorers = explorerDAO.getAll();
            lstObject.addAll(explorers);
            
			Collections.shuffle(lstObject);
			result.render("tours", lstObject);
			
		} else if (id.equalsIgnoreCase("dailytour")) {
		    if (dailyTours == null) dailyTours = dailyTourDAO.getAll();
			result.render("tours", dailyTours);
		} else if (id.equalsIgnoreCase("explorer")) {
		    if (explorers == null) explorers = explorerDAO.getAll();
			result.render("tours", explorers);
		} else {
            if (dailyTours == null) dailyTours = dailyTourDAO.getAll();
			result.render("tours", dailyTours);
		}
		return result;
	}

	public Result explorer() {
		Result result = Results.html();
		result.render("tours", explorerDAO.getAll());
		return result;
	}
}
