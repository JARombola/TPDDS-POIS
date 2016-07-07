package procesos;

import org.springframework.mail.SimpleMailMessage;
import configuracionTerminales.EnviadorMails;

public class EnvioMail implements ManejoDeResultadosProcesos{
	
	EnviadorMails enviador;
	
	@Override
	public void manejarError(Proceso proceso) {
		
		enviador.mail=new SimpleMailMessage();
		enviador.mail.setFrom("Terminal");			
		enviador.mail.setSubject("Busqueda lenta");
		enviador.mail.setText("La busqueda tardó demasiado che");
		enviador.mail.setTo(proceso.admin.getEmail());
		
		enviador.enviarMail();
	}

}
