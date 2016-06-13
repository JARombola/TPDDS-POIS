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
	
	public ArrayList<String> reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusquedas = historialBusquedas().size();
		int i;
		ArrayList<String> reporteBusquedasPorFechas=new ArrayList<String>();
		for (i = 0; i < cantBusquedas; ) {				
			LocalDate fecha=historialBusquedas().get(i).getFecha();
			List<Busqueda>busquedas=getBuscador().busquedasDeFecha(fecha);	
			i+=busquedas.size();							//suma la cantidad de resultados de la fecha
			reporteBusquedasPorFechas.add("["+fecha+"] - Cantidad Busquedas: "+busquedas.size());
		}
		//reporteBusquedasPorFechas.forEach(unaBusqueda->System.out.println(unaBusqueda));
		return reporteBusquedasPorFechas;
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
