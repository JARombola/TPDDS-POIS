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
	
	public Void actualizarPoi(Request req, Response res){
		POI viejo = Mapa.getInstancia().getPOI(Integer.parseInt(req.queryParams("id")));
		String nombreNuevo= req.queryParams("nombre");
			if(!nombreNuevo.isEmpty()) viejo.setNombre(nombreNuevo);
		String calleNueva=req.queryParams("direccion");
			if(!calleNueva.isEmpty()) viejo.getDireccion().setCalle(calleNueva);
		String altura=req.queryParams("altura");
		Long alt = Long.parseLong(altura);
			if(!altura.isEmpty()) viejo.getDireccion().setNumero(alt.intValue());
			
		String longNueva=req.queryParams("long");
		Double longNuevaDouble=Double.parseDouble(longNueva);
		if(!longNueva.isEmpty()) viejo.setLongitud(longNuevaDouble.intValue());
		
		String latNueva=req.queryParams("lat");
		Double latNuevaLong=Double.parseDouble(longNueva);
			if(!latNueva.isEmpty()) viejo.setLatitud(latNuevaLong.intValue());
			
		Mapa.getInstancia().agregarOmodificar(viejo);
		res.redirect("/adminPOIS");
		return null;
	}
	
	
	
	

}
