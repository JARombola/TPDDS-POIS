package principal.Terminales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.LocalDate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import configuracionTerminales.Administrador;
import configuracionTerminales.TiempoEjecucion;
import externos.InterfazBuscadores;
import principal.POIS.POI;


public class Buscador {
	private Mapa mapa;
	private List<Busqueda> historialBusquedas;
	private BufferBusquedas buffer;
//	private Boolean enviarMail, registrarBusquedas;		
	private Map<String,Boolean> opciones;		//Usa el map para activar/desactivar las opciones... :)
	private int tiempoMax;
	private JavaMailSender mailSender;
	private SimpleMailMessage mail;
//	private MailSender mailSender;
	private Administrador adminInterno;
	

	
	
	public List<POI> buscar(String texto1, String texto2) {
		//System.out.println("Buscó: "+texto1);
		buffer.buscar(texto1, texto2).forEach(poi->mapa.agregarOmodificar(poi));	//Primero busqueda externa				
		List<POI> resultadosBusqueda;
		resultadosBusqueda= mapa.getListaPOIS().stream()
							.filter(poi->poi.tienePalabra(texto1))
							.collect(Collectors.toList());
			
		return resultadosBusqueda;
	}
	
	

	public BufferBusquedas getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferBusquedas buffer) {
		this.buffer = buffer;
	}
	
	public void agregarExterno(InterfazBuscadores componente) {
		buffer.agregarExterno(componente);
	}
	
	public Mapa getMapa() {
		return mapa;
	}


	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
}
