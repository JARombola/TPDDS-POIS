package principal;

import otros.Administrador;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class TerminalConEnvioEmailEnLaBusqueda {
	Terminal terminal;
	double tiempoEsperaMax;
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
	
	public TerminalConEnvioEmailEnLaBusqueda(Terminal terminal, double tiempoEecucionMax) {
		this.tiempoEsperaMax=tiempoEecucionMax;
		this.terminal = terminal;
	}
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public  void buscar(String texto1, String texto2){
		
		double tiempoEjecucion=	terminal.buscar(texto1, texto2);
		
		if(tiempoEjecucion>tiempoEsperaMax){
			MimeMessage message = mailSender.createMimeMessage();
			
				Administrador adminInterno=new Administrador();
			
			MimeMessageHelper helper;
				
			try {
			
				helper = new MimeMessageHelper(message, true);

				helper.setFrom(simpleMailMessage.getFrom());
				helper.setTo(simpleMailMessage.getTo());
				helper.setSubject(simpleMailMessage.getSubject());
				helper.setText(String.format(
					simpleMailMessage.getText(), adminInterno.getEmail(), "tardo mucho la busqueda")); 
		//	this.mailSender.send(message);
					
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
