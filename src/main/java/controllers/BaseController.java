package controllers;

import java.util.List;

import model.Excursion;
import model.Explorer;
import model.RoundTrip;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.session.Session;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import dao.ExcursionDAO;
import dao.ExplorerDAO;
import dao.RoundTripDAO;

public class BaseController {

	ExcursionDAO excursionDAO;

	ExplorerDAO explorerDAO;

	RoundTripDAO roundTripDAO;

	NinjaCache ninjaCache;

	NinjaProperties ninjaProperties;

	Context context;

	Session session;

	@Inject
	public BaseController(ExcursionDAO excursionDAO, ExplorerDAO explorerDAO,
			RoundTripDAO roundTripDAO, NinjaCache ninjaCache,
			NinjaProperties ninjaProperties, Context context, Session session) {
		this.excursionDAO = excursionDAO;
		this.explorerDAO = explorerDAO;
		this.roundTripDAO = roundTripDAO;
		this.ninjaCache = ninjaCache;
		this.ninjaProperties = ninjaProperties;
		this.context = context;
		this.session = session;
	}

	public Result index() {
		Result result = Results.html();
		return result;
	}

	protected List<Excursion> getExcursions() {

		List<Excursion> excursions = ninjaCache.get("excursions", List.class);
		if (excursions == null) {
			excursions = excursionDAO.getAll();
			ninjaCache.set("excursions", excursions,
					ninjaProperties.get("cacheDuration"));
		}

		return excursions;
	}

	protected List<Explorer> getExplorers() {

		List<Explorer> explorers = ninjaCache.get("explorers", List.class);
		if (explorers == null) {
			explorers = explorerDAO.getAll();
			ninjaCache.set("explorers", explorers,
					ninjaProperties.get("cacheDuration"));
		}

		return explorers;
	}

	protected List<RoundTrip> getRoundTrips() {

		List<RoundTrip> roundtrips = ninjaCache.get("roundtrips", List.class);
		if (roundtrips == null) {
			roundtrips = roundTripDAO.getAll();
			ninjaCache.set("roundtrips", roundtrips,
					ninjaProperties.get("cacheDuration"));
		}

		return roundtrips;
	}

	protected Excursion getExcursion(String link) {

		List<Excursion> excursions = ninjaCache.get("excursions", List.class);
		Excursion tour = null;
		if (excursions == null) {
			tour = excursionDAO.findByLink(link);
		} else {
			for (Excursion excursion : excursions) {
				if (excursion.getLink().equalsIgnoreCase(link)) {
					tour = excursion;
					return tour;
				}
			}
			tour = excursionDAO.findByLink(link);
		}

		return tour;
	}

	protected Explorer getExplorer(String link) {

		List<Explorer> explorers = ninjaCache.get("explorers", List.class);
		Explorer explorer = null;
		if (explorers == null) {
			explorer = explorerDAO.findByLink(link);
		} else {
			for (Explorer data : explorers) {
				if (data.getLink().equalsIgnoreCase(link)) {
					explorer = data;
					return explorer;
				}
			}
			explorer = explorerDAO.findByLink(link);
		}

		return explorer;
	}

	protected RoundTrip getRoundTrip(String link) {

		List<RoundTrip> roundTrips = ninjaCache.get("roundtrips", List.class);
		RoundTrip roundtrip = null;
		if (roundtrip == null) {
			roundtrip = roundTripDAO.findByLink(link);
		} else {
			for (RoundTrip data : roundTrips) {
				if (data.getLink().equalsIgnoreCase(link)) {
					roundtrip = data;
					return roundtrip;
				}
			}
			roundtrip = roundTripDAO.findByLink(link);
		}

		return roundtrip;
	}

}
