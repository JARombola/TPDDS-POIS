package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import principal.Direccion;
import principal.Horario;
import principal.POI;
import tipos.Banco;
import tipos.CGP;
import tipos.Servicio;

public class BufferBusquedas {
	List<POI> resultados = new ArrayList<POI>();
	
	public List<POI> getResultados(){
		return resultados;
	}


	public  void buscar(OrigenDatos componente, String palabra, String servicio){ //Este metodo es para que no me tire error en Mapa nada mas (por no existir buscar con 3 parametros). Despues lo borro
		List<POI> lista = new ArrayList<POI>();
	}

	public  void buscar(OrigenDatos componente, String palabra){		//Una sola palabra => CGP
		List<POI> puntos=componente.buscar(palabra).stream()
								.map(unCentro->adaptarCGP(unCentro))
								.collect(Collectors.toList());
		resultados.addAll(puntos);
	}
	
	/*public static List<POI> buscarBanco(OrigenDatos componente, String banco, String servicio){		//CGP
		List<POI> puntos=componente.buscar(banco,servicio);			//IMPLEMENTAR JACKSON
		return puntos;
	}
	
	public List<POI> adaptarBancos(String){
		
	}*/
	

	public  CGP adaptarCGP(CentroDTO poiEntrada){
		CGP poiSalida=new CGP();
		poiSalida.setId(poiEntrada.getId());
		poiSalida.setNombre(poiEntrada.getDomicilio());
		poiSalida.getDireccion().setCalle(poiEntrada.getCalle());
		poiSalida.getDireccion().setNumero(poiEntrada.getNumero());
		poiEntrada.getServicios().forEach(servicioEntrada->poiSalida.agregarServicio(this.adaptarSerivicio(servicioEntrada)));
		return poiSalida;
	}
	
	public  Servicio adaptarSerivicio (ServiciosDTO servicioEntrada){
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
	
	public  Banco adaptarBanco(BancoExterno poiEntrada){
		Banco poiSalida=new Banco();
		//poiSalida.setId(poiEntrada.getId()); TODO el json no me devuelve id, es necesario asignarle?
		poiSalida.setNombre(poiEntrada.getNombre());
		poiSalida.getDireccion().setLatitud(poiEntrada.getDireccion().getLatitud());
		poiSalida.getDireccion().setLongitud(poiEntrada.getDireccion().getLongitud());
		poiSalida.setSucursal(poiEntrada.getSucursal());
		poiSalida.setGerente(poiEntrada.getGerente());		
		poiEntrada.getServicios().forEach(servicioEntrada->poiSalida.agregarServicio(this.adaptarSerivicioDeString(servicioEntrada)));
		
		return poiSalida;
	}
	
	public  Servicio adaptarSerivicioDeString (String servicioEntrada){
		Servicio servicioSalida = new Servicio(servicioEntrada);
		return servicioSalida;
	}
}
