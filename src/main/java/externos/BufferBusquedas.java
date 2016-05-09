package externos;

import java.util.ArrayList;
import java.util.List;

public class BufferBusquedas {
	List<CentroDTO> listaCGP;
	
	public List<CentroDTO> getListaCGP() {
		return listaCGP;
	}
	public void setListaCGP(List<CentroDTO> listaCGP) {
		this.listaCGP = listaCGP;
	}
	public void buscar(OrigenDatos componente, String palabra){		//CGP
		listaCGP = new ArrayList<CentroDTO>();
		setListaCGP(OrigenDatos.buscar(palabra));
	}
}
