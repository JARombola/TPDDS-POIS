package principal;


import java.util.List;
import java.util.stream.Collectors;


import org.joda.time.LocalDate;



public class Terminal{
	private Mapa mapa;
	private List<Busqueda> historialBusquedas; //teoricamente deberia ya estar ordenado por fecha porque se van guardando a medida que se hacen, pero despues veo de ordenarlo por las dudas

	public void buscar(String texto1, String texto2){
		int TIEMPO_DE_BUSQUEDA=10;			//TODO Agregar tiempos de demora
		Busqueda nuevaBusqueda=new Busqueda();
		List<POI>resultadosBusqueda=this.getMapa().buscar(texto1, texto2);
		nuevaBusqueda.setCantidadResultados(resultadosBusqueda.size());
		nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
		nuevaBusqueda.setTiempoBusqueda(TIEMPO_DE_BUSQUEDA);
		historialBusquedas.add(nuevaBusqueda);
	}
	
	public int reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusq = historialBusquedas.size();
		int i;
		for (i = 0; i < cantBusq; ) {
			
			LocalDate fecha=historialBusquedas.get(i).getFecha();
			List<Busqueda>busquedas=busquedasDeFecha(fecha);
			
			i+=busquedas.size();
			busquedas.forEach(a->System.out.println(a.getFraseBuscada()));
			System.out.println("["+fecha+"] - Cantidad busquedas: "+busquedas.size());
		}
		return i;
	}
	
	public List<Busqueda> busquedasDeFecha(LocalDate fecha){
		List<Busqueda> busquedas;
				busquedas=historialBusquedas.stream()
				.filter(busqueda->busqueda.getFecha().isEqual(fecha))
				.collect(Collectors.toList());
	return busquedas;	
	}
	
	public int cantidadTotalResultados(){		
		int cantidadResultados=getHistorialBusquedas().stream().mapToInt(a->a.getCantidadResultados()).sum();
		return cantidadResultados;
	}
	
	public void setHistorialBusquedas(List<Busqueda> busquedas) {
		this.historialBusquedas = busquedas;
	}
	public List<Busqueda> getHistorialBusquedas() {
		return historialBusquedas;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

}
