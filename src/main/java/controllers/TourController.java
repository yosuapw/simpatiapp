package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Excursion;
import model.Explorer;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.PathParam;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ExcursionDAO;
import dao.ExplorerDAO;

@Singleton
public class TourController {

	ExcursionDAO excursionDAO;

	ExplorerDAO explorerDAO;
	
	NinjaCache ninjaCache;

	NinjaProperties ninjaProperties;

	@Inject
	public TourController(ExcursionDAO excursionDAO, ExplorerDAO explorerDAO,
			NinjaCache ninjaCache, NinjaProperties ninjaProperties) {
		this.excursionDAO = excursionDAO;
		this.explorerDAO = explorerDAO;
		this.ninjaCache = ninjaCache;
		this.ninjaProperties = ninjaProperties;
	}

	public Result tour(@PathParam("id") String id) {
		Result result = Results.html();
		result.render("state", id);
		
		if (id.equalsIgnoreCase("all")) {
			List<Object> lstObject = new ArrayList<Object>();
			lstObject.addAll(getExcursions());
            lstObject.addAll(getExplorers());
            
			Collections.shuffle(lstObject);
			result.render("tours", lstObject);
			
		} else if (id.equalsIgnoreCase("excursion")) {
			result.render("tours", getExcursions());
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tours", getExplorers());
		} else {
			result.render("tours", getExcursions());
		}
		return result;
	}
	
	private List<Excursion> getExcursions() {
	    
		List<Excursion> excursions = ninjaCache.get("excursions", List.class);
		if (excursions == null) {
			excursions = excursionDAO.getAll();
			ninjaCache.set("excursions", excursions,
					ninjaProperties.get("cacheDuration"));
        }
        
		return excursions;
	}
    
    private List<Explorer> getExplorers() {
        
        List<Explorer> explorers = ninjaCache.get("explorers", List.class);
        if (explorers == null) {
            explorers = explorerDAO.getAll();
			ninjaCache.set("explorers", explorers,
					ninjaProperties.get("cacheDuration"));
        }
        
        return explorers;
    }

	private Excursion getExcursion(String link) {

		List<Excursion> excursions = ninjaCache.get("excursions", List.class);
		Excursion tour = null;
		if (excursions == null) {
			tour = excursionDAO.findByLink(link);
		} else {
			for (Excursion excursion : excursions) {
				if (excursion.getLink().equalsIgnoreCase(link)) {
					tour = excursion;
					break;
				}
			}
		}

		return tour;
	}

	private Explorer getExplorer(String link) {

		List<Explorer> explorers = ninjaCache.get("explorers", List.class);
		Explorer explorer = null;
		if (explorers == null) {
			explorer = explorerDAO.findByLink(link);
		} else {
			for (Explorer data : explorers) {
				if (data.getLink().equalsIgnoreCase(link)) {
					explorer = data;
					break;
				}
			}
		}

		return explorer;
	}

	private List<Object> getAllTours() {

		List<Object> lstObject = new ArrayList<Object>();
		lstObject.addAll(excursionDAO.getAll());
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
		
		if (id.equalsIgnoreCase("excursion")) {
			result.render("tour", getExcursion(link));
		} else {
			result.render("tour", getExplorer(link));
		}
		return result;
	}

	// ** FOR JSON REQUESTS **
	public Result getAll() {
		return Results.json().render(getAllTours());
	}

	public Result findByLink(@PathParam("id") String id,
			@PathParam("link") String link) {

		if (id.equalsIgnoreCase("excursion")) {
			return Results.json().render(excursionDAO.findByLink(link));
		} else {
			return Results.json().render(explorerDAO.findByLink(link));
		}
	}
}
