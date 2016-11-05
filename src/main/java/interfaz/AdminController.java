package interfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import pois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Mapa;

public class AdminController  implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/homeAdmin.hbs");
	}
	
	public ModelAndView POIS(Request req, Response res){
		return new ModelAndView(null, "admin/pois.hbs");
	}
	
	public ModelAndView filtrar(Request req, Response res){
		Map<String, List<POI>> model = new HashMap<>();
			String nombre = req.queryParams("nombre");
			String tipo = req.queryParams("tipo");
			List<POI> filtradosNombre = buscarNombre(nombre);
			List<POI> filtradosTipo = buscarTipo(tipo);
			filtradosNombre.removeAll(filtradosTipo);
			filtradosNombre.addAll(filtradosTipo);
			
		model.put("filtrados", filtradosNombre);
		return new ModelAndView(model, "admin/pois.hbs");
	}

	private List<POI> buscarTipo(String tipo) {
		if(!tipo.isEmpty())	return Mapa.getInstancia().filtrarTipo(tipo);
		return new ArrayList<POI>();
	}

	private List<POI> buscarNombre(String nombre) {
		if(!nombre.isEmpty()) return Mapa.getInstancia().filtrarNombre(nombre);
		return new ArrayList<POI>();
	}
	
	
	
	

}
