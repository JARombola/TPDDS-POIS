package externos;


import otros.Administrador;
import otros.Mail;
import otros.MailSender;
import otros.TiempoEjecucion;

public class BuscadorBancoExternoConEmail {
	BufferBusquedas buscador;
	double tiempoEsperaMax;
	public BuscadorBancoExternoConEmail(BufferBusquedas buscador, double tiempoEecucionMax) {
		this.tiempoEsperaMax=tiempoEecucionMax;
		this.buscador = buscador;
	}
	public  void buscar(String texto1, String texto2){
		double tiempoEjecucion= buscador.busquedaExterna(texto1, texto2);
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
