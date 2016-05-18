package externos;

import java.util.List;

import principal.Direccion;

public class BancoExterno {
	private String nombre;
	private Direccion direccion; 
	private String sucursal;
	private String gerente;
	private List<String> servicios;
	

	public String getNombre() {
		return nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public String getSucursal() {
		return sucursal;
	}
	public String getGerente() {
		return gerente;
	}
	public List<String> getServicios() {
		return servicios;
	}
	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}


}
