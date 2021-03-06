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
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.AdminController;
import controllers.ApplicationController;
import controllers.BaseController;
import controllers.BastetController;
import controllers.BookController;
import controllers.BookValidationController;
import controllers.PaypalController;
import controllers.TourController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
		router.GET().route("/").with(BaseController.class, "index");
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

		router.GET().route("/tours/{id}/{link}/booking")
				.with(BookController.class, "book");

		router.POST().route("/tours/{id}/{link}/booking")
				.with(BookController.class, "addBook");

		router.GET().route("/cart").with(BookController.class, "cart");

		router.GET().route("/checkout").with(BookController.class, "checkout");

		router.POST().route("/checkout")
				.with(BookController.class, "saveCheckout");

		router.GET().route("/book/validate/{link}")
				.with(BookValidationController.class, "validateBook");
		

		router.GET().route("/boss/confirmation")
				.with(AdminController.class, "waitConfirmation");
		

		router.GET().route("/boss/login")
				.with(AdminController.class, "login");
		

		router.POST().route("/boss/login")
				.with(AdminController.class, "doLogin");
		

		router.GET().route("/paypal/coba")
				.with(PaypalController.class, "coba");
		

		router.GET().route("/veritrans/asd")
				.with(BastetController.class, "asd");
		

		router.GET().route("/veritrans/success")
				.with(BastetController.class, "success");
		

		router.POST().route("/veritrans/noti")
				.with(BastetController.class, "noti");

		router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
		// router.GET().route("/hello_world3.json").with(ApplicationController.class,
		// "testJson");
		router.GET().route("/dailytour/list.json").with(ApplicationController.class, "getAll");
		
		
		// FOR JSON REQUEST

		router.GET().route("/service/tours/all")
				.with(TourController.class, "getAll");

		router.GET().route("/service/tours/{id}/{link}")
				.with(TourController.class, "findByLink");


		router.GET().route("/service/book/validate/{link}")
				.with(BookValidationController.class, "findBookValidation");

		router.GET().route("/service/book/confirm/{link}")
				.with(BookValidationController.class, "confirmPayment");

		router.GET().route("/service/boss/confirm/payment/list")
				.with(AdminController.class, "paymentConfirmation");

		router.POST().route("/service/boss/confirm/payment")
				.with(AdminController.class, "confirmItem");


		/*
		 * router.GET().route("/service/mail") .with(MailController.class,
		 * "sendMail");
		 */

		/*
		 * router.GET().route("/service/tours/{id}/{link}/booking")
		 * .with(BookController.class, "book");
		 */

		router.POST().route("/service/tours/{id}/{link}/booking")
				.with(BookController.class, "addBook");
		router.GET().route("/service/totalcart")
				.with(BookController.class, "getTotalCart");

		router.GET().route("/service/cart")
				.with(BookController.class, "getCarts");

		router.POST().route("/service/cart/delete")
				.with(BookController.class, "deleteCarts");
 
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
