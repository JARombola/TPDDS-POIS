package principal;

import java.util.List;
import java.util.stream.Collectors;

import externos.BufferBusquedas;
import externos.InterfazBuscadores;
import otros.TiempoEjecucion;

public class Buscador {
	private Mapa mapa;
	private HistorialBusqueda historialBusqueda;
	private BufferBusquedas buffer;
	
	
	public List<POI> buscar(String texto1, String texto2) {
		//System.out.println("Buscó: "+texto1);
		double tiempoBusqueda;
		HistorialBusqueda nuevaBusqueda=new HistorialBusqueda();
		TiempoEjecucion.Start();

		buffer.busquedaExterna(texto1, texto2);
		buffer.getResultados().forEach(poi->mapa.agregarOmodificar(poi));			//Primero busqueda externa
			
		List<POI> resultadosBusqueda;
		resultadosBusqueda= mapa.getListaPOIS().stream()
							.filter(poi->poi.tienePalabra(texto1))
							.collect(Collectors.toList());
		TiempoEjecucion.Stop();
		
		tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();

		nuevaBusqueda.setCantidadResultados(resultadosBusqueda.size());
		nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
		nuevaBusqueda.setTiempoBusqueda(tiempoBusqueda);
		historialBusqueda = nuevaBusqueda;

		return resultadosBusqueda;
	}
	
	
	// -------------------GETTERS,SETTERS-----------------
	public BufferBusquedas getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferBusquedas buffer) {
		this.buffer = buffer;
	}
	
	public void agregarExterno(InterfazBuscadores componente) {
		buffer.agregarExterno(componente);
	}
	
	public Mapa getMapa() {
		return mapa;
	}


	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public HistorialBusqueda getHistorialBusqueda() {
		return historialBusqueda;
	}


}
