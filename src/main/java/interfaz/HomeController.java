package interfaz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import configuracionTerminales.Administrador;
import pois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Terminal;

public class HomeController implements WithGlobalEntityManager, TransactionalOps{
	private boolean logueado = false;
	TerminalesController terminalesController;
	
	public void setTerminalesController(TerminalesController terminalesController) {
		this.terminalesController = terminalesController;
	}

	public static ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/home.hbs");
	}
	
	public ModelAndView logueo(Request req, Response res){			//TODO: agregar mensaje de error de logueo
		Map<String, Boolean> model = new HashMap<>();
		String user = req.queryParams("user");
		String pass = req.queryParams("pass");
		verificarAdmin(user,pass);
			if(logueado) res.redirect("/admin");
			else {
				verificarTerminal(user,pass);
				if(logueado) res.redirect("/terminal");
			}
			if(logueado) model.put("denegar", !logueado);
			else model.put("denegar", !logueado);
		logueado=false;
		return new ModelAndView(model, "home/home.hbs");
	}
	

	private void verificarAdmin(String user, String pass){
		withTransaction(() ->{
			Administrador admin = entityManager().find(Administrador.class, user);
			if (admin!= null) logueado=admin.autentificar(pass);
		});
	}
	
	private Void verificarTerminal(String user, String pass) {
		try {
			int id = Integer.parseInt(user);
		} catch (NumberFormatException e) {
			return null;//ID de la terminal es un numero, si falla es porque metio algo distinto de numeros
		}
		withTransaction(() ->{
			Terminal terminal= entityManager().find(Terminal.class, Integer.parseInt(user));
			if (terminal!= null) {
				logueado=terminal.autentificar(pass);
				terminalesController.setTerminal(terminal);
			}
		});
		return null;
	}
	
}
