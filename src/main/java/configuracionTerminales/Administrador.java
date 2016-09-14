package configuracionTerminales;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Administrador {
	
	@Id
	private String email;
	private String nombre;
	
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
}