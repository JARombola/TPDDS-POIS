package principal;

import otros.Administrador;
import otros.Mail;
import otros.MailSender;

public class TerminalConEnvioEmailEnLaBusqueda {
	Terminal terminal;
	double tiempoEsperaMax;
	public TerminalConEnvioEmailEnLaBusqueda(Terminal terminal, double tiempoEecucionMax) {
		this.tiempoEsperaMax=tiempoEecucionMax;
		this.terminal = terminal;
	}
	public  void buscar(String texto1, String texto2){
		
		double tiempoEjecucion=	terminal.buscar(texto1, texto2);
		
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
	}

}
