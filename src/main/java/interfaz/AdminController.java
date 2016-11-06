package interfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import net.sf.cglib.core.Local;
import pois.Direccion;
import pois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import terminales.Busqueda;
import terminales.Mapa;
import terminales.RepositorioTerminales;
import terminales.Terminal;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.ParadaColectivo;

public class AdminController  implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home/homeAdmin.hbs");
	}
	
	public ModelAndView POIS(Request req, Response res){
		return new ModelAndView(null, "admin/pois/pois.hbs");
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
		return new ModelAndView(model, "admin/pois/pois.hbs");
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
		return new ModelAndView(model, "/admin/pois/modificarPoi.hbs");
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
			res.redirect("/admin/POIS");
		return null;
	}
	
	public ModelAndView prepararRegistro(Request req, Response res){
		return new ModelAndView(null, "/admin/pois/poiNuevo.hbs");
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
		return null;
	}
	
	public Void eliminarPOI(Request req, Response res){
		String id=req.queryParams("id");
		int idInt=Integer.parseInt(id);
		Mapa.getInstancia().eliminarPOI(idInt);
			res.redirect("/admin/POIS");
		return null;
	}
	//--------------------->> TERMINALES <<------------------------
	
	public ModelAndView filtrarTerminales(Request req, Response res){
		Map<String, List<Terminal>> model = new HashMap<>();
		String comuna = req.queryParams("comuna");
		List<Terminal> terminales=RepositorioTerminales.getInstancia().getTerminales();
		model.put("terminales", terminales);
		return new ModelAndView(model, "/admin/terminales/terminales.hbs");
	}
	
	public ModelAndView detallesTerminal(Request req, Response res){
		Map<String, Terminal> model = new HashMap<>();
		int idTerminal=Integer.parseInt(req.queryParams("id"));
		Terminal buscado=RepositorioTerminales.getInstancia().getTerminal(idTerminal);
		model.put("detalles", buscado);
		return new ModelAndView(model, "/admin/terminales/detalles.hbs");
	}
	
	public ModelAndView prepararModifTerminal(Request req, Response res){
		Map<String, Terminal> model = new HashMap<>();
		Terminal terminal = RepositorioTerminales.getInstancia().getTerminal(Integer.parseInt(req.queryParams("id")));
		model.put("datos", terminal);
		return new ModelAndView(model, "/admin/terminales/modificar.hbs");
	}
	
	public Void actualizarTerminal(Request req, Response res){
		Terminal terminal = RepositorioTerminales.getInstancia().getTerminal(Integer.parseInt(req.queryParams("id")));
		String nombre = req.queryParams("nombre");
		String comuna = req.queryParams("comuna");
		String pass = req.queryParams("pass");
			terminal.setNombre(nombre);
			terminal.setPass(pass);
		String mail = req.queryParams("mail");
		String historial = req.queryParams("historial");
				if(mail!=null) terminal.activarOpcion("mail");
				if(historial!=null) terminal.activarOpcion("historial");
		res.redirect("/admin/terminales/");
		return null;
	}
	
	public Void eliminarTerminal(Request req, Response res){
		int id = Integer.parseInt(req.queryParams("id"));
		RepositorioTerminales.getInstancia().eliminarTerminal(id);
		res.redirect("/admin/terminales/");
		return null;
	}
	
	public ModelAndView prepararRegistroTerminal(Request req, Response res){
		return new ModelAndView(null, "/admin/terminales/nueva.hbs");
	}
	
	public Void registrarTerminal(Request req, Response res){
		String nombre= req.queryParams("nombre");
		String comuna=req.queryParams("comuna");			//TODO:
		String pass = req.queryParams("pass");
		String mail = req.queryParams("mail");
		String historial = req.queryParams("historial");
		Boolean persistida=false;
		Terminal terminalNueva = new Terminal();
			terminalNueva.setNombre(nombre);
			terminalNueva.setPass(pass);
			if(mail!=null) {
				terminalNueva.activarOpcion("mail");
				persistida=true;
			}
			if(historial!=null){
				terminalNueva.activarOpcion("historial");
				persistida=true;
			}
		if(!persistida) RepositorioTerminales.getInstancia().actualizar(terminalNueva);
		res.redirect("/admin/terminales/");
		return null;
	}
	//---------------------->>> CONSULTAS <<<------------------------
	
	public ModelAndView consultas(Request req, Response res){
		Map<String, List<Terminal>> model = new HashMap<>();
		List<Terminal> terminales = RepositorioTerminales.getInstancia().getTerminales();
		model.put("terminales", terminales);
		return new ModelAndView(model, "/admin/consultas/consultas.hbs");
	}
	
	public ModelAndView filtrarConsultas(Request req, Response res){
		Map<String, List<Busqueda>> model = new HashMap<>();
		String terminalID = req.queryParams("id");
		String fechaDesde= req.queryParams("desde");
		String fechaHasta= req.queryParams("hasta");
		String cantResultados = req.queryParams("cantidad");
		int id;
		LocalDate desde = null,hasta = null;
		Terminal terminal = null;
		if (!terminalID.isEmpty()) {
			id = Integer.parseInt(terminalID);
			terminal=RepositorioTerminales.getInstancia().getTerminal(id);
		}
		if(!fechaDesde.isEmpty() && !fechaHasta.isEmpty()){
			desde=new LocalDate(fechaDesde);
			hasta=new LocalDate(fechaHasta);
		}
		List<Busqueda> busquedas;
		if(terminal!=null){
			if(desde!=null){
				busquedas = terminal.busquedasIntervalo(desde, hasta);
				}
			else{
				busquedas = terminal.getHistorialBusquedas();
			}
		}
		else{
			if(desde!=null){
				busquedas = RepositorioTerminales.getInstancia().getBusquedasIntervalo(desde, hasta);}
			else{
				busquedas=RepositorioTerminales.getInstancia().getBusquedas();
			}
		}
		if(!cantResultados.isEmpty()){
			int cantidadResultados=Integer.parseInt(cantResultados);
			busquedas = busquedas.stream()
				.filter(b->(b.getCantidadResultados()==cantidadResultados))
				.collect(Collectors.toList());
		}
		model.put("busquedas", busquedas);
		return new ModelAndView(model, "admin/consultas/consultas.hbs");
	}
		
}
