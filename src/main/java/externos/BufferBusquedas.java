package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import json.JsonFactory;
import principal.Horario;
import principal.POI;
import tipos.Banco;
import tipos.CGP;
import tipos.Servicio;

public class BufferBusquedas {
	List<POI> resultados = new ArrayList<POI>();
	private JsonFactory jsonFactory = new JsonFactory();
	
	//-----------------------BUSQUEDAS-----------------------------------
	public  void buscar(OrigenDatos componente, String palabra, String servicio){ //Este metodo es para que no me tire error en Mapa nada mas (por no existir buscar con 3 parametros). Despues lo borro
		String jsonBancos=componente.buscar(palabra,servicio);
		List<POI> puntos= adaptarJsonBanco(jsonBancos);
		resultados.addAll(puntos);
	}

	public  void buscar(OrigenDatos componente, String palabra){		//Una sola palabra => CGP
		List<POI> puntos=componente.buscar(palabra).stream()
								.map(unCentro->adaptarCGP(unCentro))
								.collect(Collectors.toList());
		resultados.addAll(puntos);
	}
	
	//----------------------------CGPS EXTERNOS--------------------------------
	public  CGP adaptarCGP(CentroDTO poiEntrada){
		CGP poiSalida=new CGP();
		poiSalida.setId(poiEntrada.getId());
		poiSalida.setNombre(poiEntrada.getDomicilio());
		poiSalida.getDireccion().setCalle(poiEntrada.getCalle());
		poiSalida.getDireccion().setNumero(poiEntrada.getNumero());
		poiEntrada.getServicios().forEach(servicioEntrada->poiSalida.agregarServicio(this.adaptarServicio(servicioEntrada)));
		return poiSalida;
	}
	
	public  Servicio adaptarServicio (ServiciosDTO servicioEntrada){
		List<Horario> horarios = new ArrayList<Horario>();
		Servicio servicioSalida = new Servicio(servicioEntrada.getNombre());
		horarios = servicioEntrada.getRangos().stream().map(rango -> adaptarAHorarioLocalTime(rango)).collect(Collectors.toList());
		servicioSalida.getHorarios().setHorariosAtencion(horarios);
		return servicioSalida;
	}
	
	public  Horario adaptarAHorarioLocalTime(RangosServiciosDTO rangoEntrada){
		Horario horarioSalida = new Horario();
		LocalTime horaInicio= new LocalTime(rangoEntrada.getHoraInicio(),rangoEntrada.getMinutoInicio());
		LocalTime horaFin= new LocalTime(rangoEntrada.getHoraFin(),rangoEntrada.getMinutoFin());
		horarioSalida.setInicio(horaInicio);
		horarioSalida.setFin(horaFin);
		horarioSalida.setDia(rangoEntrada.getDia());
		return horarioSalida;
	}
	//---------------------------BANCOS EXTERNOS---------------------------------------
	public List<POI> adaptarJsonBanco(String jsonBanco){
			List<BancoExterno> bancoext = jsonFactory.fromJson(jsonBanco);
		 	List<POI>bancos=bancoext.stream().map(banco->adaptarBanco(banco)).collect(Collectors.toList());
		 	return bancos;
	}
	private Banco adaptarBanco(BancoExterno externo){
		Banco poiSalida=new Banco();
	 	//poiSalida.setId(externo.getId()); TODO el json no me devuelve id, es necesario asignarle?
	 	poiSalida.setNombre(externo.getNombre());
	 	poiSalida.getDireccion().setLatitud(externo.getDireccion().getLatitud());
	 	poiSalida.getDireccion().setLongitud(externo.getDireccion().getLongitud());
	 	poiSalida.setSucursal(externo.getSucursal());
	 	poiSalida.setGerente(externo.getGerente());		
	 	externo.getServicios().forEach(servicioEntrada->poiSalida.agregarServicio(this.adaptarSerivicioDeString(servicioEntrada)));
		return poiSalida;
	}
		 	
	public  Servicio adaptarSerivicioDeString (String servicioEntrada){
		Servicio servicioSalida = new Servicio(servicioEntrada);
		return servicioSalida;
	}
	//------------------GETTERS, SETTERS
	public BufferBusquedas(){
	}
	public List<POI> getResultados(){
		return resultados;
	}
}
