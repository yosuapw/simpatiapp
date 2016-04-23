/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import java.util.List;

import model.Excursion;
import ninja.Result;
import ninja.Results;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ExcursionDAO;


@Singleton
public class ApplicationController {
    
    ExcursionDAO dailyTourDAO;
    
    @Inject
    public ApplicationController(ExcursionDAO dailyTourDAO) {
        this.dailyTourDAO = dailyTourDAO;
    }

    public Result index() {

        return Results.html();

    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
	/*
	 * public Result testJson() { long result = dailyTourDAO.countAll(); return
	 * Results.json().render(result); }
	 */

	public Result getAll() {
		List<Excursion> result = dailyTourDAO.getAll();
		return Results.json().render(result);
	}

    public static class SimplePojo {

        public String content;
        
    }
}
