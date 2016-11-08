package interfaz;

import interfaz.TerminalesController;
import interfaz.AdminController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.ListHelper;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("size", ListHelper.size)
				.build();

		Spark.staticFiles.location("/public");
		
		TerminalesController terminalesController = new TerminalesController();
		AdminController adminController = new AdminController();
		HomeController homeController = new HomeController();
		homeController.setTerminalesController(terminalesController);
		
		Spark.get("/", HomeController::home, engine); //user y pass
		
		Spark.post("/login", homeController::logueo, engine); //user y pass
		Spark.get("/terminal", terminalesController::home,engine);
		Spark.get("/terminal/", terminalesController::buscarPOI, engine);
		Spark.get("/terminal/detalles", terminalesController::mostrarDetalles, engine);
		
		Spark.get("/admin", adminController::home, engine);
		Spark.get("/admin/POIS", adminController::POIS, engine);
		Spark.get("/admin/POIS/", adminController::filtrar, engine);
		Spark.get("/admin/POIS/modificar", adminController::prepararModif,engine);
		Spark.post("/admin/POIS/modif", adminController::actualizarPoi);
		Spark.get("/admin/POIS/registrar", adminController::prepararRegistro,engine);
		Spark.post("/admin/POIS/registrar", adminController::registrarPOI);
		Spark.get("/admin/POIS/eliminar", adminController::eliminarPOI);
		
		Spark.get("/admin/terminales", adminController::terminales,engine);
		Spark.get("/admin/terminales/", adminController::filtrarTerminales,engine);
		Spark.get("/admin/terminales/detalles", adminController::detallesTerminal,engine);
		Spark.get("/admin/terminales/modificar", adminController::prepararModifTerminal,engine);
		Spark.post("/admin/terminales/modif", adminController::actualizarTerminal);
		Spark.get("/admin/terminales/eliminar", adminController::eliminarTerminal);
		Spark.get("/admin/terminales/registrar", adminController::prepararRegistroTerminal,engine);
		Spark.post("/admin/terminales/registrar", adminController::registrarTerminal);
		
		Spark.get("/admin/consultas", adminController::consultas,engine);
		Spark.get("/admin/consultas/", adminController::filtrarConsultas,engine);
		Spark.get("/admin/consultas/resultados", adminController::resultadosConsulta,engine);
		Spark.get("/admin/consultas/eliminar", adminController::eliminarConsulta);
		
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
