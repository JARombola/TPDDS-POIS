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
	
	public ControlProcesos getControlador() {
		return controladorProcesos;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}

	public void setEmail(String email) {
		this.email=email;
	}
}