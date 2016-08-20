package principal.Terminales;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import configuracionTerminales.FuncionesExtra;
import principal.POIS.Comuna;
import principal.POIS.Coordenadas;
import principal.POIS.POI;


public class Terminal{
	private Coordenadas coordenadas;
	private String nombre;
	private Mapa mapa;
	private List<Busqueda> historialBusquedas;
	private FuncionesExtra extra;
	private BufferBusquedas buffer;
	
	public List<POI> iniciarBusqueda(String texto1, String texto2){
		extra.inicioBusqueda();
		
		List<POI> resultadosBusqueda = buscar(texto1, texto2);
		Busqueda nuevaBusqueda=new Busqueda();						//Registro la busqueda realizada
		nuevaBusqueda.setCantidadResultados(resultadosBusqueda.size());
		nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
		
		extra.finBusqueda(nuevaBusqueda);
		return resultadosBusqueda;
	}

	public List<POI> buscar(String texto1, String texto2) {
		buffer.buscar(texto1, texto2).forEach(poi->mapa.agregarOmodificar(poi));	//Primero busqueda externa				
		List<POI> resultadosBusqueda;
		resultadosBusqueda= mapa.getListaPOIS().stream()
							.filter(poi->poi.tienePalabra(texto1))
							.collect(Collectors.toList());
			
		return resultadosBusqueda;
	}
	
	public POI getPOI(int id){
		return mapa.getPOI(id);
	}

	//----------------------REPORTES---------------------------------------
	public List<DatosReporte> reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusquedas = getHistorialBusquedas().size();
		int i;
		List<DatosReporte> reporteBusquedasPorFechas=new ArrayList<DatosReporte>();
		for (i = 0; i < cantBusquedas; ) {				
			LocalDate fecha=getHistorialBusquedas().get(i).getFecha();
			List<Busqueda>busquedas=this.busquedasDeFecha(fecha);	
			DatosReporte busquedasFecha=this.crearReporteFechas(busquedas);
			reporteBusquedasPorFechas.add(busquedasFecha);
			i+=busquedas.size();							//suma la cantidad de resultados de la fecha
		}
		//reporteBusquedasPorFechas.forEach(unaBusqueda->System.out.println("["+unaBusqueda.getFecha()+"]"+"-Terminal: "+unaBusqueda.getTerminal()+" |Resultados: "+unaBusqueda.getDatos()));
		return reporteBusquedasPorFechas;
	}
	
	public List<Busqueda> busquedasDeFecha(LocalDate fecha){
		List<Busqueda> busquedas;
				busquedas=getHistorialBusquedas().stream()
				.filter(busqueda->busqueda.getFecha().isEqual(fecha))
				.collect(Collectors.toList());
	return busquedas;
	}
	
	public List<DatosReporte> obtenerResultadosParciales() {
		List<Busqueda> datosBusquedas=getHistorialBusquedas();
		List<DatosReporte> reporte=new ArrayList<DatosReporte>();
		datosBusquedas.stream().forEach(busqueda->{
			DatosReporte reporteParciales=new DatosReporte();
			reporteParciales.setDatos(busqueda.getCantidadResultados());
			reporte.add(reporteParciales);
			}
		);
		return reporte;
	}
	
	public DatosReporte cantidadTotalResultados(){
		if (!getHistorialBusquedas().isEmpty()){
		List<Busqueda> datosBusquedas=getHistorialBusquedas();
		DatosReporte reporteBusquedasTotales=this.crearReporte(datosBusquedas);
		return reporteBusquedasTotales;}
		return new DatosReporte();
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
	private DatosReporte crearReporteFechas(List<Busqueda> busquedas){
		DatosReporte datos=new DatosReporte();
		datos.setFecha(busquedas.get(0).getFecha());
		datos.setTerminal(this.getNombre());
		datos.setDatos(busquedas.size());
		return datos;
	}
		
	public void activarOpcion(String opcion){
		getExtra().activarOpcion(opcion);
	}
	public void desactivarOpcion(String opcion){
		getExtra().desactivarOpcion(opcion);
	}

	// -------------------GETTERS,SETTERS-----------------
	public boolean estaEnLaComuna(Comuna unaComuna){
		return (unaComuna.dentroDeLaZona(getCoordenadas()));
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
	public List<Busqueda> getHistorialBusquedas() {
		return historialBusquedas;
	}
	public void setHistorialBusquedas(List<Busqueda> historialBusquedas) {
		this.historialBusquedas = historialBusquedas;
	}
	public FuncionesExtra getExtra() {
		return extra;
	}
	public void setExtra(FuncionesExtra extra) {
		this.extra = extra;
	}

	public void guardarBusquedas(Busqueda unaBusqueda){
		getHistorialBusquedas().add(unaBusqueda);
	}

	public BufferBusquedas getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferBusquedas buffer) {
		this.buffer = buffer;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

}
