package principal.Terminales;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import externos.InterfazBuscadores;
import principal.POIS.POI;

public class BufferBusquedas {
	Set<POI> resultados;
	List<InterfazBuscadores> buscadoresComponentes;
	

	public BufferBusquedas(){
		resultados=new HashSet<POI>();
		buscadoresComponentes= new ArrayList<InterfazBuscadores>();
	}
	
	public List<POI> buscar(String texto1, String texto2){		//Asincronico, entonces...
		List<POI>resultadosEnBuffer=resultados.stream()					//Primero busca en los resultados que tiene
									.filter(poi->(poi.tienePalabra(texto1)||poi.tienePalabra(texto2)))
									.collect(Collectors.toList());
		
	/*	resultados.removeIf(poi->{						//Los que le mando a la terminal los saco de este buffer
			return resultadosEnBuffer.contains(poi); 
			}
		);*/
		
		if(resultadosEnBuffer.isEmpty()){			//Si ninguno sirvi� => consulta a los externos
			buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
			getBuscadoresComponentes().forEach(componente -> {
				resultadosEnBuffer.addAll(componente.getResultado());
				resultados.addAll(componente.getResultado());			//Guardo los del externo
				}
			);
		}
		return resultadosEnBuffer;
	}
	/*
	public void busquedaExterna (String texto1, String texto2){
		buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
	}
	
	public List<POI> getResultados(){
		buscadoresComponentes.forEach(componente -> resultados.addAll(componente.getResultado()));
		return resultados;
	}
	*/
	public void agregarExterno(InterfazBuscadores componente) {
		this.buscadoresComponentes.add(componente);
	}
	
	public List<InterfazBuscadores> getBuscadoresComponentes() {
		return buscadoresComponentes;
	}

	
	
}
