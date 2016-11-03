package interfaz;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import configuracionTerminales.Administrador;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Terminal;

public class HomeController implements WithGlobalEntityManager, TransactionalOps{
	private boolean logueado;
	
	public static ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/home.hbs");
	}
	
	public ModelAndView logueo(Request req, Response res){
		String user = req.queryParams("user");
		String pass = req.queryParams("pass");
		verificarAdmin(user,pass);
			if(logueado) res.redirect("/admin");
			else {
				verificarTerminal(user,pass);
				if(logueado) res.redirect("/terminal");
			}
		logueado=false;
		return new ModelAndView(null, "home/home.hbs");
	}
	

	private void verificarAdmin(String user, String pass){
		withTransaction(() ->{
			Administrador admin = entityManager().find(Administrador.class, user);
			if (admin!= null) logueado=admin.autentificar(pass);
		});
	}
	
	private void verificarTerminal(String user, String pass) {
		try {
			int id = Integer.parseInt(user);
		} catch (NumberFormatException e) {
			//ID de la terminal es un numero, si falla es porque metio algo distinto de numeros
		}
		withTransaction(() ->{
			Terminal terminal= entityManager().find(Terminal.class, Integer.parseInt(user));
			if (terminal!= null) logueado=terminal.autentificar(pass);
		});
	}
	
}
