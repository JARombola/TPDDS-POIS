package principal.Terminales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.LocalDate;
import org.springframework.mail.MailSender;
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
	

	
	public Buscador(){
		mailSender=new JavaMailSenderImpl();
		opciones=new HashMap<String,Boolean>();
		opciones.put("HISTORIAL", false);
		opciones.put("MAIL", false);
		this.adminInterno=new Administrador();
		this.adminInterno.setEmail("juli_fula@hotmail.com");
		this.configurarMail();
	}
	private void configurarMail(){
		this.mail=new SimpleMailMessage();
		this.mail.setFrom("Terminal");			//CHAN...
		this.mail.setSubject("Busqueda lenta");
		this.mail.setText("La busqueda tardó demasiado che");
		this.mail.setTo(adminInterno.getEmail());
	}
	
	public List<POI> buscar(String texto1, String texto2) {
		//System.out.println("Buscó: "+texto1);
		double tiempoBusqueda;
		if(getOpciones().get("HISTORIAL") || getOpciones().get("MAIL")){
			TiempoEjecucion.Start();
		}
			buffer.buscar(texto1, texto2).forEach(poi->mapa.agregarOmodificar(poi));	//Primero busqueda externa
				
		List<POI> resultadosBusqueda;
		resultadosBusqueda= mapa.getListaPOIS().stream()
							.filter(poi->poi.tienePalabra(texto1))
							.collect(Collectors.toList());
	
		if(getOpciones().get("HISTORIAL") || getOpciones().get("MAIL")){
			TiempoEjecucion.Stop();
			tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();
			if(getOpciones().get("HISTORIAL")){
				Busqueda nuevaBusqueda=new Busqueda();						//Registro la busqueda realizada
				nuevaBusqueda.setCantidadResultados(resultadosBusqueda.size());
				nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
				nuevaBusqueda.setTiempoBusqueda(tiempoBusqueda);
				historialBusquedas.add(nuevaBusqueda);
			}
			
			if (getOpciones().get("MAIL")&&	tiempoBusqueda>getTiempoMax()){
				this.enviarMail();
				
			}
		}
		
		return resultadosBusqueda;
	}
	
	public List<Busqueda> busquedasDeFecha(LocalDate fecha){
		List<Busqueda> busquedas;
				busquedas=getHistorialBusqueda().stream()
				.filter(busqueda->busqueda.getFecha().isEqual(fecha))
				.collect(Collectors.toList());
	return busquedas;
	}
	
	public int cantidadTotalResultados(){		
		int cantidadResultados=getHistorialBusqueda().stream()
								.mapToInt(busqueda->busqueda.getCantidadResultados())
								.sum(); 
		return cantidadResultados;
	}
	
	public void activarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), true);
	}
	
	public void desactivarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), false);
	}
	
	
	public Busqueda ultimaBusqueda(){
		return getHistorialBusqueda().get(historialBusquedas.size()-1);
	}
	
	// -------------------GETTERS,SETTERS-----------------
	private void enviarMail(){
		
		MimeMessage message = mailSender.createMimeMessage();			 			
		MimeMessageHelper helper;
		 try {		
			 helper = new MimeMessageHelper(message, true);
		 	 helper.setFrom(mail.getFrom());
		 	 helper.setTo(mail.getTo());
		 	 helper.setSubject(mail.getSubject());
		 	 helper.setText(mail.getText()); 
		 //	 mailSender.send(message);
		     System.out.println("Mail enviado");
		 					
		 	} catch (MessagingException e) {
		 		// TODO Auto-generated catch block
		 		e.printStackTrace();
		 	}
	}
	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.mail = simpleMailMessage;
		}
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public Map<String, Boolean> getOpciones() {
		return opciones;
	}
	
	public int getTiempoMax() {
		return tiempoMax;
	}

	public void setTiempoMax(int tiempoMax) {
		this.tiempoMax = tiempoMax;
	}


	public void setOpciones(Map<String, Boolean> opciones) {
		this.opciones = opciones;
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
	
	public List<Busqueda> getHistorialBusqueda() {
		return historialBusquedas;
	}

	
	public void setHistorialBusquedas(List<Busqueda> almacenamientoBusquedas) {
		this.historialBusquedas=almacenamientoBusquedas;
		
	}

}
