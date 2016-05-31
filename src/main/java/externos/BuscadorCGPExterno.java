package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import otros.Administrador;
import otros.Mail;
import otros.MailSender;
import otros.TiempoEjecucion;
import principal.Horario;
import principal.POI;
import tipos.CGP;
import tipos.Servicio;

public class BuscadorCGPExterno implements InterfazBuscadores {
	OrigenDatos componente;
	List<POI> resultado= new ArrayList();;
	int tiempoEsperaMax=2;
	public  void buscar(String texto1, String texto2){	
		if (texto2 == ""){
		double tiempoEjecucion;
		TiempoEjecucion.Start();
		List<POI> puntos=componente.buscar(texto1).stream()
								.map(unCentro->adaptarCGP(unCentro))
								.collect(Collectors.toList());
	
		TiempoEjecucion.Stop();
		tiempoEjecucion=TiempoEjecucion.getTiempoEjecucion();
		if(tiempoEjecucion>tiempoEsperaMax){
			Administrador adminInterno=new Administrador();
			Mail mail = new Mail();
			mail.setFrom(adminInterno.getEmail());
		//agregar parametro Admin
			//mail.setTo(admin.getEmail());
			mail.setMessage("tardo mucho la busqueda");
			mail.setSubject("Aviso busqueda lenta");
			MailSender mailSender = null ;
			mailSender.send(mail);
		}
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
	
	public void setComponente(OrigenDatos componente) {
		this.componente = componente;
	}

}
