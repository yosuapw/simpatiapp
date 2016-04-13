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
		result.render("state", id);
		
		if (id.equalsIgnoreCase("all")) {
			List<Object> lstObject = new ArrayList<Object>();
			lstObject.addAll(getDailyTours());
            lstObject.addAll(getExplorers());
            
			Collections.shuffle(lstObject);
			result.render("tours", lstObject);
			
		} else if (id.equalsIgnoreCase("dailytour")) {
			result.render("tours", getDailyTours());
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tours", getExplorers());
		} else {
			result.render("tours", getDailyTours());
		}
		return result;
	}
	
	private List<DailyTour> getDailyTours() {
	    
        List<DailyTour> dailyTours = ninjaCache.get("dailyTours", List.class);
        if (dailyTours == null) { 
            dailyTours = dailyTourDAO.getAll();
            ninjaCache.set("dailyTours", dailyTours);
        }
        
        return dailyTours;
	}
    
    private List<Explorer> getExplorers() {
        
        List<Explorer> explorers = ninjaCache.get("explorers", List.class);
        if (explorers == null) {
            explorers = explorerDAO.getAll();
            ninjaCache.set("explorers", explorers);
        }
        
        return explorers;
    }

	public Result explorer() {
		Result result = Results.html();
		result.render("tours", explorerDAO.getAll());
		return result;
	}
}
