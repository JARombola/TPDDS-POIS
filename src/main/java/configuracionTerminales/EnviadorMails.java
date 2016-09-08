package configuracionTerminales;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import procesos.Proceso;

public class EnviadorMails {
	private JavaMailSender mailSender;
	public SimpleMailMessage mail;
	private Administrador adminInterno;
	
	public EnviadorMails(){
		mailSender=new JavaMailSenderImpl();
		this.adminInterno=new Administrador();
		this.adminInterno.setEmail("Admin@hotmail.com");		
	}
	
	public void mailBusquedaLenta(){
		this.mail=new SimpleMailMessage();
		this.mail.setFrom("Terminal");			//CHAN...
		this.mail.setSubject("Busqueda lenta");
		this.mail.setText("La busqueda tardó demasiado");
		this.mail.setTo(adminInterno.getEmail());
	}
	
	public void mailFallaProceso(Proceso proceso) {
	    String nombre = proceso.getClass().getName();
		mail=new SimpleMailMessage();
		mail.setFrom("Manejo de Resultados de los Procesos");			
		mail.setSubject("Error en el proceso "+nombre);
		mail.setText("el proceso "+nombre+" ejecutado el dia "+ proceso.getFecha()+ " Fallo su ejecucion.");
		mail.setTo(proceso.admin.getEmail()); 
	}	
	
	
	public void enviarMail(){
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
}
