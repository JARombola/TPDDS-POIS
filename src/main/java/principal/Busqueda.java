package principal;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import principal.POI;

public class Busqueda {
	private String frase;
	public List<POI> resultados = new ArrayList();
	private int tiempoBusqueda;
	private Date fecha; 


	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}