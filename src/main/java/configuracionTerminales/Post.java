package configuracionTerminales;

public class Post {
	public Post() {
		
	}
	Administrador admin;
	String mensaje;
	public Administrador getUsuario() {
		return admin;
	}
	
	public void setUsuario(Administrador admin) {
		this.admin=admin;
	}
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje=mensaje;
	}
}
