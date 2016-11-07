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
import terminales.Terminal;

public class TerminalesController  implements WithGlobalEntityManager, TransactionalOps{
	Terminal terminal;

	public ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/homeTerminal.hbs");
	}
	
	public ModelAndView buscarPOI(Request req, Response res){
		Map<String, List<POI>> model = new HashMap<>();
		String txt1 = req.queryParams("texto1");
		String txt2 = req.queryParams("texto2");
		List<POI>resultados=buscarPalabra(txt1,txt2);
		model.put("pois", resultados);
		return new ModelAndView(model, "home/homeTerminal.hbs");
	}
		
		private List<POI> buscarPalabra(String texto1, String texto2){
			if(!(texto1.isEmpty() || texto2.isEmpty())) return terminal.realizarBusqueda(texto1, texto2);
			return new ArrayList<POI>();
		}
	
	public ModelAndView mostrarDetalles(Request req, Response res){
		Map<String, POI> model = new HashMap<>();
		int idPoi=Integer.parseInt(req.queryParams("id"));
		POI buscado=Mapa.getInstancia().getPOI(idPoi);
		model.put("detalles", buscado);
		return new ModelAndView(model, "/detallesPoi.hbs");
	}
	
	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
}
