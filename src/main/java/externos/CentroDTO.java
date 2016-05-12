package externos;

import java.util.List;

public class CentroDTO {
	int comuna;
	String zona, domicilio, telefono;
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	List<ServiciosDTO> servicios;
	
}
