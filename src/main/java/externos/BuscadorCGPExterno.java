package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import principal.Horario;
import principal.POI;
import tipos.CGP;
import tipos.Servicio;

public class BuscadorCGPExterno {
	OrigenDatos componente;
	List<POI> resultado= new ArrayList();;
	
	public  void buscar(String texto1, String texto2){	
		if (texto2 == ""){
		List<POI> puntos=componente.buscar(texto1).stream()
								.map(unCentro->adaptarCGP(unCentro))
								.collect(Collectors.toList());
		resultado.addAll(puntos);
		}
	}
	
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

	
	public  Servicio adaptarSerivicioString (String nombre){
		Servicio servicioSalida = new Servicio(nombre);
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
	
	public List<POI> getResultado() {
		return resultado;
	}

}
