package procesos;

import org.springframework.mail.SimpleMailMessage;

import configuracionTerminales.EnviadorMails;

public class EnvioMail {
	
	EnviadorMails enviador;
	
	//@Override
	public void MandarMailDeFallaProceso(Proceso proceso) {
	    String nombre = proceso.getClass().getName();
		enviador.mail=new SimpleMailMessage();
		enviador.mail.setFrom("Manejo de Resultados de los Procesos");			
		enviador.mail.setSubject("Error en el proceso "+nombre);
		enviador.mail.setText("el proceso "+nombre+" ejecutado el dia "+ proceso.getFecha()+ " Fallo su ejecucion.");
		enviador.mail.setTo(proceso.admin.getEmail());
		
		enviador.enviarMail();
		
	}

}
