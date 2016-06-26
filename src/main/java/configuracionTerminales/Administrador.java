package configuracionTerminales;
import principal.Terminales.Terminal;
import procesos.ControlProcesos;

public class Administrador {
	
	private ControlProcesos controladorProcesos;
	private String email;
	private String nombre;
	private Terminal terminal;
	
	public Administrador() {
		terminal = new Terminal();
		controladorProcesos = new ControlProcesos();
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}	
	
	
	public Terminal getTerminal() {
		return terminal;
	}
	
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
}
