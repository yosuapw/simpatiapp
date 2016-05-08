package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.PathParam;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ExcursionDAO;
import dao.ExplorerDAO;
import dao.RoundTripDAO;

@Singleton
public class TourController extends BaseController {

	@Inject
	public TourController(ExcursionDAO excursionDAO, ExplorerDAO explorerDAO,
			RoundTripDAO roundTripDAO, NinjaCache ninjaCache,
			NinjaProperties ninjaProperties, Context context) {
		super(excursionDAO, explorerDAO, roundTripDAO, ninjaCache,
				ninjaProperties, context);
	}

	public Result tour(@PathParam("id") String id) {
		Result result = Results.html();
		result.render("state", id);
		
		if (id.equalsIgnoreCase("all")) {
			List<Object> lstObject = new ArrayList<Object>();
			lstObject.addAll(getExcursions());
            lstObject.addAll(getExplorers());
			lstObject.addAll(getRoundTrips());
            
			Collections.shuffle(lstObject);
			result.render("tours", lstObject);
			
			result.render("user", context.getSession().get("username"));
		} else if (id.equalsIgnoreCase("excursion")) {
			result.render("tours", getExcursions());
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tours", getExplorers());
		} else if (id.equalsIgnoreCase("roundtrip")) {
			result.render("tours", getRoundTrips());
		} else {
			result.render("tours", getExcursions());
		}
		return result;
	}

	private List<Object> getAllTours() {

		List<Object> lstObject = new ArrayList<Object>();
		lstObject.addAll(getExcursions());
		lstObject.addAll(getExplorers());
		lstObject.addAll(getRoundTrips());
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
		context.getSession().put("username", "kevin");
		
		if (id.equalsIgnoreCase("excursion")) {
			result.render("tour", getExcursion(link));
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tour", getExplorer(link));
		} else {
			result.render("tour", getRoundTrip(link));
		}
		return result;
	}


	public Result transportation() {
		Result result = Results.html();
		return result;
	}

	// ** FOR JSON REQUESTS **
	public Result getAll() {
		return Results.json().render(getAllTours());
	}

	public Result findByLink(@PathParam("id") String id,
			@PathParam("link") String link) {

		if (id.equalsIgnoreCase("excursion")) {
			return Results.json().render(getExcursion(link));
		} else if (id.equalsIgnoreCase("explorer")) {
			return Results.json().render(getExplorer(link));
		} else {
			return Results.json().render(getRoundTrip(link));
		}
	}
}
