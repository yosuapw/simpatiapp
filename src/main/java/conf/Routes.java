/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import ninja.AssetsController;
import ninja.Results;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;
import controllers.TourController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
		router.GET().route("/").with(Results.html().template("/views/index.html"));
		/*
		 * router.GET().route("/tours")
		 * .with(Results.html().template("/views/directory-list.html"));
		 */
		router.GET().route("/transportation-and-rental")
				.with(TourController.class, "transportation");

		router.GET().route("/tours/{id}")
				.with(TourController.class, "tour");

		router.GET().route("/tours/{id}/{link}")
				.with(TourController.class, "detail");

		/*
		 * router.GET().route("/tours/explorers") .with(TourController.class,
		 * "explorers");
		 */

		router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
		// router.GET().route("/hello_world3.json").with(ApplicationController.class,
		// "testJson");
		router.GET().route("/dailytour/list.json")
				.with(ApplicationController.class, "getAll");
		
		

		router.GET().route("/service/tours/all")
				.with(TourController.class, "getAll");

		router.GET().route("/service/tours/{id}/{link}")
				.with(TourController.class, "findByLink");
        
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
