package terminales;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import configuracionTerminales.FuncionesExtra;
import pois.Comuna;
import pois.Coordenadas;
import pois.POI;


public class Terminal{
	private Coordenadas coordenadas;
	private String nombre;
	private Mapa mapa;
	private List<Busqueda> historialBusquedas;
	private FuncionesExtra extra;
	private BufferBusquedas buffer;
	
	public Terminal(){
		extra = new FuncionesExtra(10);
		extra.setTerminal(this);
	}
	
	public List<POI> realizarBusqueda(String texto1, String texto2){
		extra.inicioBusqueda();

		List<POI> resultadosBusqueda = buscar(texto1, texto2);
		Busqueda nuevaBusqueda=new Busqueda();						//Registro la busqueda realizada
			nuevaBusqueda.setFecha(LocalDate.now());
			nuevaBusqueda.setResultados(resultadosBusqueda);
			nuevaBusqueda.setFraseBuscada(texto1+" "+texto2);
		
		extra.finBusqueda(nuevaBusqueda);
		return resultadosBusqueda;
	}

	public List<POI> buscar(String texto1, String texto2) {
		buffer.buscar(texto1, texto2).forEach(poi->mapa.agregarOmodificar(poi));	//Primero busqueda externa				
		List<POI> resultadosBusqueda= mapa.getListaPOIS().stream()
									.filter(poi->poi.tienePalabra(texto1))
									.collect(Collectors.toList());
			
		return resultadosBusqueda;
	}
	
	public POI getPOI(int id){
		return mapa.getPOI(id);
	}

	//----------------------REPORTES---------------------------------------
	public Reporte reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		int cantBusquedas = getHistorialBusquedas().size();
		int i;
		Reporte reporteBusquedasPorFechas = new Reporte("Reporte Fechas");
		reporteBusquedasPorFechas.setTerminal(getNombre());
		for (i = 0; i < cantBusquedas;) {
			LocalDate fecha = getHistorialBusquedas().get(i).getFecha();
			List<Busqueda> busquedas = this.busquedasDeFecha(fecha);
			DatosReporte busquedasFecha = this.crearReporteFechas(busquedas);
			reporteBusquedasPorFechas.agregarDatos(busquedasFecha);
			i+=busquedas.size();							//suma la cantidad de resultados de la fecha
		}
		//reporteBusquedasPorFechas.forEach(unaBusqueda->System.out.println("["+unaBusqueda.getFecha()+"]"+"-Terminal: "+unaBusqueda.getTerminal()+" |Resultados: "+unaBusqueda.getDatos()));
		return reporteBusquedasPorFechas;
	}
	
	public List<Busqueda> busquedasDeFecha(LocalDate fecha) {
		List<Busqueda> busquedas;
		busquedas = getHistorialBusquedas().stream()
				.filter(busqueda -> busqueda.getFecha().isEqual(fecha))
				.collect(Collectors.toList());
		return busquedas;
	}
	
	private DatosReporte crearReporteFechas(List<Busqueda> busquedas){
		DatosReporte datos=new DatosReporte();
			datos.setFecha(busquedas.get(0).getFecha());
			datos.setResultados(busquedas.size());
		return datos;
	}
	
	public Reporte obtenerResultadosParciales() {
		List<Busqueda> datosBusquedas = getHistorialBusquedas();
		
		Reporte reporte = new Reporte("Resultados Parciales");
		reporte.setTerminal(getNombre());
		datosBusquedas.stream().forEach(busqueda -> {
						DatosReporte reporteParciales = new DatosReporte();
						reporteParciales.setResultados(busqueda.getCantidadResultados());
						reporteParciales.setFecha(busqueda.getFecha());
						reporte.agregarDatos(reporteParciales);}
		);
		
		return reporte;
	}
	
	public Reporte cantidadTotalResultados() {
		List<Busqueda> datosBusquedas = getHistorialBusquedas();
		if (getHistorialBusquedas().isEmpty()) return new Reporte();
		
		Reporte reporteBusquedasTotales = crearReporte(datosBusquedas);
		return reporteBusquedasTotales;
	}
	
	private Reporte crearReporte(List<Busqueda> busquedas) {
		Reporte reporte=new Reporte("Total Resultados");
			reporte.setTerminal(this.getNombre());
			reporte.agregarDatos(busquedas.get(0).getFecha(),
					 busquedas.stream()
					 .mapToInt(datosBusqueda->datosBusqueda.getResultados().size())
					 .sum());
		return reporte;
	}
	
	
		
	public void activarOpcion(String opcion){
		getExtra().activarOpcion(opcion);
	}
	public void desactivarOpcion(String opcion){
		getExtra().desactivarOpcion(opcion);
	}
	public void guardarBusquedas(Busqueda unaBusqueda){
		if (this.historialBusquedas==null) historialBusquedas= new ArrayList<Busqueda>();
		getHistorialBusquedas().add(unaBusqueda);
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
