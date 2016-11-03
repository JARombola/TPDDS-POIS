package interfaz;


import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import pois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Mapa;

public class TerminalesController  implements WithGlobalEntityManager, TransactionalOps{

	public ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/homeTerminal.hbs");
	}
	
	public ModelAndView buscarPOI(Request req, Response res){
		Map<String, POI> model = new HashMap<>();
		String txt1 = req.queryParams("texto1");
		String txt2 = req.queryParams("texto2");
		POI poi = Mapa.getInstancia().buscarPoi(txt1).get(0);
		
		model.put("pois", poi);
		return new ModelAndView(model, "home/homeTerminal.hbs");
	}
	
}
