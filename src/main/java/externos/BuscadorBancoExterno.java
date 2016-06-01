package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import json.JsonFactory;
import principal.POI;
import tipos.Banco;
import tipos.Servicio;

public class BuscadorBancoExterno implements InterfazBuscadores {
	OrigenDatos componente;
	

	List<POI> resultado= new ArrayList<POI>();
	

	private JsonFactory jsonFactory = new JsonFactory();
	
	public void buscar(String texto1, String texto2){
		if (texto2 != ""){
			String jsonBancos=componente.buscar(texto1,texto2);
		List<POI> puntos= adaptarJsonBanco(jsonBancos);
		resultado.addAll(puntos);
		}
	}
	
	//------------------ADAPTER----------------------------------------
	public List<POI> adaptarJsonBanco(String jsonBanco){
		List<BancoExterno> bancoext = jsonFactory.fromJson(jsonBanco);
	 	List<POI> bancos = bancoext.stream().map(banco->adaptarBanco(banco)).collect(Collectors.toList());
	 	return bancos;
	 	}
	
	private Banco adaptarBanco(BancoExterno externo){
		Banco poiSalida=new Banco();
	 	poiSalida.setId(externo.getId());
	 	poiSalida.setNombre(externo.getNombre());
	 	poiSalida.getDireccion().setLatitud(externo.getDireccion().getLatitud());
	 	poiSalida.getDireccion().setLongitud(externo.getDireccion().getLongitud());
	 	poiSalida.setSucursal(externo.getSucursal());
	 	poiSalida.setGerente(externo.getGerente());		
	 	externo.getServicios().forEach(servicioEntrada->poiSalida.agregarServicio(this.adaptarSerivicioDeString(servicioEntrada)));
		return poiSalida;
	}
		 	
	public  Servicio adaptarSerivicioDeString (String servicioEntrada){
		Servicio servicioSalida = new Servicio(servicioEntrada);
		return servicioSalida;
	}
	
	public List<POI> getResultado() {
		return resultado;
	}
	
	public void setComponente(OrigenDatos componente) {
		this.componente = componente;
	}

}
