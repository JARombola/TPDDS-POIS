package externos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalTime;

import json.JsonFactory;
import principal.Horario;
import principal.POI;
import tipos.Banco;
import tipos.CGP;
import tipos.Servicio;

public class BufferBusquedas {
	List<POI> resultados;
	List<InterfazBuscadores> buscadoresComponentes = new ArrayList();
	
	public void busquedaExterna (String texto1, String texto2){
		buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
	}
	
	public List<POI> getResultados(){
		resultados = new ArrayList<POI>();
		buscadoresComponentes.forEach(componente -> resultados.addAll(componente.getResultado()));
		return resultados;
	}
	
	public void agregarExterno(InterfazBuscadores componente) {
		this.buscadoresComponentes.add(componente);
	}
	
	
}
