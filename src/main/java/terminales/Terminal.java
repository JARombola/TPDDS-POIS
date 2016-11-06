package terminales;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalDate;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import configuracionTerminales.Administrador;
import configuracionTerminales.FuncionesExtra;
import pois.Comuna;
import pois.Coordenadas;
import pois.POI;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Terminal {
	@Id @GeneratedValue
	private int id;
	
	@OneToOne @JoinColumn @Cascade(value=CascadeType.ALL)
	private Administrador administrador;
	
	@Embedded
	private Coordenadas coordenadas;
	
	@OneToOne @Cascade(value=CascadeType.ALL)
	private FuncionesExtra extra;

	@Transient
	private Mapa mapa;
	
	@Transient
	private BufferBusquedas buffer;

	private String nombre;
	
	private String pass;

	@Transient
	Morphia morphia;
	@Transient
	MongoClient mongo;
	@Transient
	Datastore store;

	public Terminal(){
		extra = new FuncionesExtra(0);
		extra.setTerminal(this);
		coordenadas = new Coordenadas();
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
		List<POI> resultadosBusqueda = null;
		if(buffer!=null){
			resultadosBusqueda = buffer.buscar(texto1, texto2);
		}
		resultadosBusqueda.addAll(mapa.buscarPoi(texto1));
		return resultadosBusqueda;
	}
	
	public POI getPOI(int id){
		return mapa.getPOI(id);
	}

	//----------------------REPORTES---------------------------------------
	public Reporte reporteFechas(){ 		//Calcula cantidad de busquedas de todas las fechas
		iniciarMorphia();
		int i, cantBusquedas = cantidadBusquedas();
		List<Busqueda> busquedas;
		Reporte reporteBusquedasPorFechas = new Reporte("Reporte Fechas");
		reporteBusquedasPorFechas.setTerminal(getNombre());
		LocalDate fecha;
		for (i = 0; i < cantBusquedas; i+=busquedas.size()) {
			fecha = fechaBusqueda(i);
			busquedas = busquedasDeFecha(fecha);
			DatosReporte busquedasFecha = crearReporteFechas(busquedas);
			reporteBusquedasPorFechas.agregarDatos(busquedasFecha);
		}
		return reporteBusquedasPorFechas;
	}
	
	public List<Busqueda> busquedasDeFecha(LocalDate fecha) {
		List<Busqueda> busquedas;
		busquedas = store.createQuery(Busqueda.class)
				.filter("fecha =", fecha)
				.asList();
		return busquedas;
	}
	
	private DatosReporte crearReporteFechas(List<Busqueda> busquedas){
		DatosReporte datos=new DatosReporte();
			datos.setFecha(busquedas.get(0).getFecha());
			datos.setResultados(busquedas.size());
		return datos;
	}
	
	public Reporte reporteResultadosParciales() {
		iniciarMorphia();
		List<Busqueda> datosBusquedas = store.find(Busqueda.class).asList();
		
		Reporte reporte = new Reporte("Resultados Parciales");
		reporte.setTerminal(getNombre());
		datosBusquedas.stream()
					.forEach(busqueda -> 
					reporte.agregarDatos(busqueda.getFecha(), busqueda.getCantidadResultados()));
		
		return reporte;
	}
	
	public Reporte reporteTotalResultados() {
		iniciarMorphia();
		List<Busqueda> datosBusquedas = store.find(Busqueda.class).asList();
		if (datosBusquedas.isEmpty()) return new Reporte();
		
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
		getOpciones().activarOpcion(opcion);
	}
	
	public void desactivarOpcion(String opcion){
		getOpciones().desactivarOpcion(opcion);
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
		return store.find(Busqueda.class).asList();
	}

	public FuncionesExtra getOpciones() {
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
//---------------------------------------BD Busquedas-----------------------------------	
	private int cantidadBusquedas(){
		return (int)store.getCount(Busqueda.class);
	}
	
	private void iniciarMorphia(){
		String pathMongoBusquedas = "B_"+nombre;
		morphia = new Morphia();
		morphia.getMapper().getOptions().setStoreNulls(true);
		morphia.mapPackage("pois");
		morphia.mapPackage("tiposPoi");
		morphia.mapPackage("terminales");
		morphia.getMapper().getConverters().addConverter( new LocalDateConverter() );
		mongo = new MongoClient();
		store = morphia.createDatastore(mongo, pathMongoBusquedas);
	}
	
	public void eliminarBusquedas(){
		iniciarMorphia();
		String pathMongoBusquedas = "B_"+nombre;
		mongo.dropDatabase(pathMongoBusquedas);
	}
	
	private LocalDate fechaBusqueda(int posicion){
		return store.find(Busqueda.class).asList().get(posicion).getFecha();
	}

	public boolean autentificar(String pass) {
		return pass.equals(this.pass);
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
