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

public class TerminalesController  implements WithGlobalEntityManager, TransactionalOps{

	public ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/homeTerminal.hbs");
	}
	
	public ModelAndView buscarPOI(Request req, Response res){
		Map<String, List<POI>> model = new HashMap<>();
		String txt1 = req.queryParams("texto1");
		String txt2 = req.queryParams("texto2");
		List<POI>resultados=buscarPalabra(txt1);
		List<POI>resultados2 = buscarPalabra(txt2);
		resultados.removeAll(resultados2);
		resultados.addAll(resultados2);
		model.put("pois", resultados);
		return new ModelAndView(model, "home/homeTerminal.hbs");
	}
		
		private List<POI> buscarPalabra(String texto){
			if(!texto.isEmpty()) return Mapa.getInstancia().buscarPoi(texto);
			return new ArrayList<POI>();
		}
	
	public ModelAndView mostrarDetalles(Request req, Response res){
		Map<String, POI> model = new HashMap<>();
		int idPoi=Integer.parseInt(req.queryParams("id"));
		POI buscado=Mapa.getInstancia().getPOI(idPoi);
		model.put("detalles", buscado);
		return new ModelAndView(model, "/detallesPoi.hbs");
	}
	
}
