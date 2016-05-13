package externos;

import java.util.List;
import java.util.stream.Collectors;

import principal.POI;
import tipos.CGP;

public class BufferBusquedas {
	List<CentroDTO> listaCGP;
	
	public List<CentroDTO> getListaCGP() {
		return listaCGP;
	}
	
	public void setListaCGP(List<CentroDTO> listaCGP) {
		this.listaCGP.addAll(listaCGP);
	}



	public List<POI> buscar(OrigenDatos componente, String palabra){		//Una sola palabra: CGP
		List<POI> puntos=componente.buscar(palabra).stream()
								.map(unCentro->adaptar(unCentro))
								.collect(Collectors.toList());
		return puntos;
	}
	/*public static List<POI> buscarBanco(OrigenDatos componente, String banco, String servicio){		//CGP
		List<POI> puntos=componente.buscar(banco,servicio);			//IMPLEMENTAR JACKSON
		return puntos;
	}*/
	

	public POI adaptar(CentroDTO a){
		CGP nuevoPoi=new CGP();
		nuevoPoi.setNombre(a.getDomicilio());
		nuevoPoi.getDireccion().setCalle(a.getCalle());
		nuevoPoi.getDireccion().setNumero(a.getNumero());
		return nuevoPoi;
	}
}
