package principal;


import java.util.List;
import java.util.stream.Collectors;


import org.joda.time.LocalDate;

import otros.TiempoEjecucion;

public class Terminal{
	private String nombre;
	private Mapa mapa;
	private List<HistorialBusqueda> historialBusquedas; //teoricamente deberia ya estar ordenado por fecha porque se van guardando a medida que se hacen, pero despues veo de ordenarlo por las dudas
	private Buscador buscador;
	
	

	public List<POI> buscar(String texto1, String texto2){
		
		double TIEMPO_DE_BUSQUEDA;
		HistorialBusqueda nuevaBusqueda=new HistorialBusqueda();
		
		TiempoEjecucion.Start();
		List<POI> resultadosBusqueda = buscador.buscar(texto1, texto2);
		TiempoEjecucion.Stop();
		TIEMPO_DE_BUSQUEDA= TiempoEjecucion.getTiempoEjecucion();

		nuevaBusqueda.setCantidadResultados(resultadosBusqueda.size());
		nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
		nuevaBusqueda.setTiempoBusqueda(TIEMPO_DE_BUSQUEDA);
		historialBusquedas.add(nuevaBusqueda);
		
		return resultadosBusqueda;
		
	}
	
	public int reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusquedas = historialBusquedas.size();
		int i;
		for (i = 0; i < cantBusquedas; ) {				//va a mostrar todas las busquedas POR fecha
			
			LocalDate fecha=historialBusquedas.get(i).getFecha();
			List<HistorialBusqueda>busquedas=busquedasDeFecha(fecha);
			
			i+=busquedas.size();					//suma la cantidad de resultados de la fecha
			busquedas.forEach(unaBusqueda->System.out.println(unaBusqueda.getFraseBuscada()));
			System.out.println("["+fecha+"] - Cantidad busquedas: "+busquedas.size());
		}
		return i;
	}
	
	public List<HistorialBusqueda> busquedasDeFecha(LocalDate fecha){
		List<HistorialBusqueda> busquedas;
				busquedas=historialBusquedas.stream()
				.filter(busqueda->busqueda.getFecha().isEqual(fecha))
				.collect(Collectors.toList());
	return busquedas;	
	}
	
	public int cantidadTotalResultados(){		
		int cantidadResultados=getHistorialBusquedas().stream().mapToInt(a->a.getCantidadResultados()).sum(); //TODO verificar
		return cantidadResultados;
	}
	
	// -------------------GETTERS,SETTERS-----------------
	
	public HistorialBusqueda getUltimaBusqueda(){
		return historialBusquedas.get(historialBusquedas.size()-1);
	}
	
	public void setHistorialBusquedas(List<HistorialBusqueda> busquedas) {
		this.historialBusquedas = busquedas;
	}
	public List<HistorialBusqueda> getHistorialBusquedas() {
		return historialBusquedas;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	
	public Buscador getBuscador() {
		return buscador;
	}

	public void setBuscador(Buscador buscador) {
		this.buscador = buscador;
	}

}
