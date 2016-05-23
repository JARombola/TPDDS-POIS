package externos;

import java.util.List;

import principal.Direccion;

public class BancoExterno {
	private String nombre;
	private Direccion direccion; 
	private String sucursal;
	private String gerente;
	private List<String> servicios;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BancoExterno(){
		this.direccion=new Direccion();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public void setGerente(String gerente) {
		this.gerente = gerente;
	}
	
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
