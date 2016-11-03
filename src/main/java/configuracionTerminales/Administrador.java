package configuracionTerminales;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Administrador {
	
	@Id 
	private String email;
	private String nombre;
	private String pass;
	
	public Administrador() {
	}
	
	public Administrador(String mail) {
		this.email=mail;
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
		this.nombre = nombre;
	}

	public boolean autentificar(String pass) {
		return pass.equals(this.pass);
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}