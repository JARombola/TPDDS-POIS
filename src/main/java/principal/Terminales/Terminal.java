package principal.Terminales;


import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import principal.POIS.POI;


public class Terminal{
	private String nombre;
	private Mapa mapa;
	private Buscador buscador;
//	private List<Busqueda> historialBusquedas;
	
	

	public List<POI> buscar(String texto1, String texto2){
		List<POI> resultadosBusqueda = buscador.buscar(texto1, texto2);
		return resultadosBusqueda;
	}
	
	public List<DatosReporte> reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusquedas = historialBusquedas().size();
		int i;
		List<DatosReporte> reporteBusquedasPorFechas=new ArrayList<DatosReporte>();
		for (i = 0; i < cantBusquedas; ) {				
			LocalDate fecha=historialBusquedas().get(i).getFecha();
			List<Busqueda>busquedas=getBuscador().busquedasDeFecha(fecha);	
			DatosReporte busquedasFecha=this.crearReporte(busquedas);
			reporteBusquedasPorFechas.add(busquedasFecha);
			i+=busquedas.size();							//suma la cantidad de resultados de la fecha
		}
		//reporteBusquedasPorFechas.forEach(unaBusqueda->System.out.println("["+unaBusqueda.getFecha()+"]"+"-Terminal: "+unaBusqueda.getTerminal()+" |Resultados: "+unaBusqueda.getDatos()));
		return reporteBusquedasPorFechas;
	}
	
	
	private DatosReporte crearReporte(List<Busqueda> busquedas) {
		DatosReporte datos=new DatosReporte();
		datos.setFecha(busquedas.get(0).getFecha());
		datos.setTerminal(this.getNombre());
		datos.setDatos(busquedas.stream()
				 .mapToInt(datosBusqueda->datosBusqueda.getCantidadResultados())
				 .sum());
		return datos;
	}

	public int cantidadTotalResultados(){		
		int cantidadResultados=getBuscador().cantidadTotalResultados(); 
		return cantidadResultados;
	}
	
	public List<Busqueda> historialBusquedas(){
		return buscador.getHistorialBusqueda();
	}
	
	// -------------------GETTERS,SETTERS-----------------
	
	public Busqueda getUltimaBusqueda(){
		return getBuscador().ultimaBusqueda();
	}
	
	public void activarOpcion(String opcion){
		getBuscador().activarOpcion(opcion);
	}
	public void desactivarOpcion(String opcion){
		getBuscador().desactivarOpcion(opcion);
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
