package externos;

import java.util.ArrayList;
import java.util.List;

public class ServiciosDTO {
	private String nombre;
	private List<RangosServiciosDTO> rangos;
	
	public ServiciosDTO() {
		rangos = new ArrayList<RangosServiciosDTO>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<RangosServiciosDTO> getRangos() {
		return rangos;
	}
	
	public void setRangos(List<RangosServiciosDTO> rangos) {
		this.rangos = rangos;
	}
	
	public void agregarRango(RangosServiciosDTO rango){
		rangos.add(rango);
	}
	
	
	
	
}
