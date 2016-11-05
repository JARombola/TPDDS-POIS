package interfaz;

import interfaz.TerminalesController;
import interfaz.AdminController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();

		Spark.staticFiles.location("/public");
		
		TerminalesController terminalesController = new TerminalesController();
		AdminController adminController = new AdminController();
		HomeController homeController = new HomeController();
		
		Spark.get("/", HomeController::home, engine); //user y pass
		
		Spark.post("/login", homeController::logueo, engine); //user y pass
		Spark.get("/terminal", terminalesController::home,engine);
		Spark.get("/terminal/", terminalesController::buscarPOI, engine);
		Spark.get("/terminal/detalles", terminalesController::mostrarDetalles, engine);
		
		Spark.get("/admin", adminController::home, engine);
		Spark.get("/adminPOIS", adminController::POIS, engine);
		Spark.get("/adminPOIS/", adminController::filtrar, engine);
		
/*		Spark.get("/administrador/pois", adminController::mostrarPois, engine);
		Spark.get("/administrador/pois/editar", adminController::editarPoi, engine);
		Spark.get("/administrador/terminales", adminController::mostrarTerminales, engine);
		Spark.get("/administrador/terminales/agregar", adminController::agregarTerminal, engine);
		Spark.get("/administrador/terminales/editar", adminController::editarTerminal, engine);
		Spark.get("/administrador/terminales/acciones", adminController::configurarAcciones, engine);
		Spark.get("/administrador/historial", adminController::mostrarHistorial, engine);
		Spark.get("/administrador/historial/:id", adminController::consultaPois, engine);
		*/
	}

}
