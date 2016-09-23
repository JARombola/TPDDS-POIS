package terminales;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalDate;

import configuracionTerminales.Administrador;
import configuracionTerminales.FuncionesExtra;
import pois.Comuna;
import pois.Coordenadas;
import pois.POI;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Terminal{
	@Id @GeneratedValue
	private int id;
	
	@OneToOne @JoinColumn @Cascade(value=CascadeType.ALL)
	private Administrador administrador;
	
	@Embedded
	private Coordenadas coordenadas;
	
	@OneToOne @Cascade(value=CascadeType.ALL)
	private FuncionesExtra extra;

	private String nombre;
	@OneToMany @Cascade(value=CascadeType.ALL)
	private List<Busqueda> historialBusquedas;

	@Transient
	private Mapa mapa;
	
	@Transient
	private BufferBusquedas buffer;

	public Terminal(){
		extra = new FuncionesExtra(0);
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
		int i, cantBusquedas = getHistorialBusquedas().size();
		List<Busqueda> busquedas;
		Reporte reporteBusquedasPorFechas = new Reporte("Reporte Fechas");
		reporteBusquedasPorFechas.setTerminal(getNombre());
		LocalDate fecha;
		for (i = 0; i < cantBusquedas; i+=busquedas.size()) {
			fecha = getHistorialBusquedas().get(i).getFecha();
			busquedas = this.busquedasDeFecha(fecha);
			DatosReporte busquedasFecha = this.crearReporteFechas(busquedas);
			reporteBusquedasPorFechas.agregarDatos(busquedasFecha);
		}
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
	
	public Reporte reporteResultadosParciales() {
		List<Busqueda> datosBusquedas = getHistorialBusquedas();
		
		Reporte reporte = new Reporte("Resultados Parciales");
		reporte.setTerminal(getNombre());
		datosBusquedas.stream()
					.forEach(busqueda -> reporte.agregarDatos(busqueda.getFecha(), busqueda.getCantidadResultados()));
		
		return reporte;
	}
	
	public Reporte reporteTotalResultados() {
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
	
	
		
	public void activarOpcion(String opcion) throws Exception {
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
	
	public boolean estaActivado(String opcion){
		return getExtra().estaActivado(opcion);
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
		extra.setTerminal(this);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

}
