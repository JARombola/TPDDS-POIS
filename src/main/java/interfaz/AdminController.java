package interfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import net.sf.cglib.core.Local;
import pois.Direccion;
import pois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Mapa;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.ParadaColectivo;

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
	
	public ModelAndView prepararModif(Request req, Response res){
		Map<String, POI> model = new HashMap<>();
		POI viejo = Mapa.getInstancia().getPOI(Integer.parseInt(req.queryParams("id")));
		model.put("detalles", viejo);
		return new ModelAndView(model, "/admin/modificarPoi.hbs");
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
		
			System.out.println("MODIFICADO");
		Mapa.getInstancia().agregarOmodificar(viejo);
			res.redirect("/admin/POIS");
		return null;
	}
	
	public ModelAndView prepararRegistro(Request req, Response res){
		return new ModelAndView(null, "/admin/poiNuevo.hbs");
	}
	
	public Void registrarPOI(Request req, Response res) throws Exception{
		POI poiNuevo;
		String tipo=req.queryParams("tipo");
		switch (tipo.toUpperCase()){
			case "BANCO": poiNuevo = new Banco(); break;
			case "PARADA": poiNuevo = new ParadaColectivo(); break;
			case "LOCAL": poiNuevo= new tiposPoi.Local(); break;
			case "CGP": poiNuevo = new CGP(); break;
			default: throw new Exception("Tipo de POI Incorrecto!");
		}
		String calle=req.queryParams("calle");
		int altura=Integer.parseInt(req.queryParams("altura"));
		double latitud=Double.parseDouble(req.queryParams("lat"));
		double longitud=Double.parseDouble(req.queryParams("long"));
		String nombre=req.queryParams("nombre");
		Direccion dire=new Direccion();
			dire.setCalle(calle);
			dire.setNumero(altura);
			dire.setLongitud(longitud);
			dire.setLatitud(latitud);
		poiNuevo.setDireccion(dire);
		poiNuevo.setNombre(nombre);
		Mapa.getInstancia().agregarOmodificar(poiNuevo);
		res.redirect("/admin/POIS");				//TODO: cambiar direccion cuando se cree el menu
		System.out.println("POI Registrado");
		return null;
	}
	
	public Void eliminarPOI(Request req, Response res){
		String id=req.queryParams("id");
		int idInt=Integer.parseInt(id);
		Mapa.getInstancia().eliminarPOI(idInt);
			res.redirect("/admin/POIS");
		return null;
	}
	
	
	
	
	

}
