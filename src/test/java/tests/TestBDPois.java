package tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import configuracionTerminales.Administrador;
import configuracionTerminales.FuncionesExtra;
import externos.BuscadorBancoExterno;
import externos.BuscadorCGPExterno;
import externos.OrigenDatos;
import pois.Comuna;
import pois.Coordenadas;
import pois.Direccion;
import pois.Horario;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.Mapa;
import terminales.Reporte;
import terminales.Terminal;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;
import tiposPoi.Rubro;
import tiposPoi.Servicio;

public class TestBDPois extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private EntityManager em;
	
	ParadaColectivo parada1, parada2, parada3;
	Mapa mapa;
	BufferBusquedas buffer;
	BuscadorBancoExterno buscadorBanco;
	BuscadorCGPExterno buscadorCgp;
	OrigenDatos origenBanco,origenCGP;
	Terminal terminal;
	Administrador admin;
	
	@Before
	public void init(){
		em = PerThreadEntityManagers.getEntityManager();
	}
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	@Test
	public void testPersistirDireccion(){		
		beginTransaction();
			Direccion dire= new Direccion();
			dire.setBarrio("Devoto");
			dire.setCalle("Beiro");
			dire.setDpto(20);
			dire.setLatitud(99);
			dire.setLongitud(2);
			persist(dire);
			commitTransaction();
		em.clear();
		
		Direccion direccionBuscada = (Direccion) em.createQuery("from Direccion where Barrio = :barrio")
				.setParameter("barrio", "Devoto").getSingleResult();
		assertEquals(direccionBuscada.getBarrio(),"Devoto");
		assertEquals(direccionBuscada.getCalle(),"Beiro");
		assertEquals(direccionBuscada.getLongitud(),2,0);
		assertEquals(direccionBuscada.getLatitud(),99,0);
		}
	
	
	@Test
	public void testPersistirParadaColectivo(){
		beginTransaction();
		ParadaColectivo parada1 = new ParadaColectivo();
			parada1.agregarTag("colectivo");
			parada1.agregarTag("lento");
			parada1.agregarTag("aburrido");
			parada1.setNombre("PARADA COLECTIVO");
		Direccion dire= new Direccion();
			dire.setLatitud(10);
			dire.setLongitud(20);
			dire.setBarrio("Palermo");
			parada1.setDireccion(dire);
		persist(parada1);
		commitTransaction();
		em.clear();		
		
		ParadaColectivo paradaBuscada = (ParadaColectivo) em.createQuery("from ParadaColectivo where Nombre = :nombre")
				.setParameter("nombre", "PARADA COLECTIVO")
				.getSingleResult();
		assertEquals(paradaBuscada.getTags().get(2),"aburrido");
		assertEquals(paradaBuscada.getTags().size(), 3);
		assertEquals(paradaBuscada.getDireccion().getLatitud(),10,0);
		assertEquals(paradaBuscada.getDireccion().getBarrio(),"Palermo");
		}		
	
	@Test
	public void testPersistenciaBanco(){
		beginTransaction();
			Banco banco = new Banco();
			banco.setNombre("Patagonia");
			Servicio jubilacion = new Servicio("JUBILACION");
				jubilacion.setDescripcion("$$$$$$");
				jubilacion.agregarTag("Plata");
			Horario horaJubilacion=new Horario();
				horaJubilacion.setDia(1);
			LocalTime horaInicio=new LocalTime(10,00);
			LocalTime horaCierre=new LocalTime(22,00);
				horaJubilacion.setInicio(horaInicio);
				horaJubilacion.setFin(horaCierre);
			jubilacion.agregarHorario(horaJubilacion);
			banco.agregarServicio(jubilacion);
			banco.setGerente("Julian");
			banco.setLatitud(222);
			
		persist(banco);
		commitTransaction();
		em.clear();		
		
		Banco bancoBuscado = (Banco) em.createQuery("from Banco where Gerente = :gerente")
				.setParameter("gerente", "Julian")
				.getSingleResult();
		assertEquals(bancoBuscado.getGerente(),"Julian");
		assertEquals(bancoBuscado.getDireccion().getLatitud(),222,0);	// => Persistio la Direccion => Persistio Coordenadas :)
		assertEquals(bancoBuscado.getServicios().getServicios().size(),1,0);	// Persistió los servicios
		assertTrue(bancoBuscado.estaDisponible(1,new LocalTime(11,00),"JUBILACION"));	//Persistió tambien sus horarios :)
		
	}	
	
	@Test
	public void testPersistenciaCGP(){
		beginTransaction();
			CGP cgp = new CGP();
			cgp.setNombre("CGPlal");
			Servicio jubilacion = new Servicio("JUBILACION");
				jubilacion.setDescripcion("$$$$$$");
				jubilacion.agregarTag("Plata");
			Horario horaJubilacion=new Horario();
				horaJubilacion.setDia(1);
			LocalTime horaInicio=new LocalTime(10,00);
			LocalTime horaCierre=new LocalTime(22,00);
				horaJubilacion.setInicio(horaInicio);
				horaJubilacion.setFin(horaCierre);
			jubilacion.agregarHorario(horaJubilacion);
			cgp.agregarServicio(jubilacion);
			cgp.setLatitud(200);
			Comuna comuna = new Comuna();
			comuna.setNombre("Comuna 8");
			cgp.setComuna(comuna);
			
		persist(cgp);
		commitTransaction();
		em.clear();		
		
		CGP cgpBuscado = (CGP) em.createQuery("from CGP where Nombre = :nombre")
				.setParameter("nombre", "CGPlal")
				.getSingleResult();
		assertEquals(cgpBuscado.getNombre(),"CGPlal");
		assertEquals(cgpBuscado.getDireccion().getLatitud(),200,0);
		assertEquals(cgpBuscado.getServicios().getServicios().size(),1,0);
		assertEquals(cgpBuscado.getComuna().getNombre(),"Comuna 8");
		assertTrue(cgpBuscado.estaDisponible(1,new LocalTime(11,00),"JUBILACION"));	
		
	}
	
	@Test
	public void testPersistenciaLocal(){
		beginTransaction();
			Local local = new Local();
			local.setLatitud(47);
			local.setLongitud(-122);
			Rubro libreria = new Rubro("libros");
			libreria.setRadioCercania(0.3);
			local.setRubro(libreria);
			local.setNombre("Nicolo");
			LocalTime horaInicio;
			LocalTime horaCierre;
			
			for (int dia = 2; dia <= 7; dia++) {
				horaInicio=new LocalTime(10,00);
				horaCierre=new LocalTime(13,00);
				local.getHorarios().horarioNuevo(dia, horaInicio, horaCierre);
				horaInicio=new LocalTime(17,00);
				horaCierre=new LocalTime(20,30);
				local.getHorarios().horarioNuevo(dia, horaInicio, horaCierre);
			}

		persist(local);
		commitTransaction();
		em.clear();		
		
		Local localBuscado = (Local) em.createQuery("from Local where Nombre = :nombre")
				.setParameter("nombre", "Nicolo")
				.getSingleResult();
		assertEquals(localBuscado.getRubro().getNombre(),"libros");
		assertEquals(localBuscado.getDireccion().getLatitud(),47,0);
		assertEquals(localBuscado.getDireccion().getLongitud(),-122,0);
		assertEquals(localBuscado.getRubro().getRadioCercania(),0.3,0);
		assertTrue(localBuscado.estaDisponible(3,new LocalTime(19,00),""));	
		
		
	}
	
	public void inicializarBusquedas() throws Exception{
		admin = new Administrador("MAIL@mail");
		buscadorBanco=new BuscadorBancoExterno();
		buscadorCgp=new BuscadorCGPExterno();
		origenBanco =Mockito.mock(OrigenDatos.class);
		buscadorBanco.setComponente(origenBanco);
		origenCGP =Mockito.mock(OrigenDatos.class);
		buscadorCgp.setComponente(origenCGP);
		parada1 = new ParadaColectivo();
		parada2 = new ParadaColectivo();
		parada3 = new ParadaColectivo();
			parada1.setNombre("primer parada de la linea 114");
			parada2.setNombre("segunda parada de la linea 114");
			parada3.setNombre("tercera parada de la linea 114");
			parada3.agregarTag("Lento");
			parada3.agregarTag("Llegas tarde");
			parada3.agregarTag("Feo");
		mapa = new Mapa();
			mapa.setPOI(parada1);
			mapa.setPOI(parada2);
			mapa.setPOI(parada3);
		
		buffer=new BufferBusquedas();
			buffer.agregarExterno(buscadorBanco);
			buffer.agregarExterno(buscadorCgp);
			
		terminal = new Terminal();
		terminal.setNombre("Terminal 1");
			terminal.setBuffer(buffer);
			terminal.setMapa(mapa);
			terminal.setAdministrador(admin);
			
			terminal.activarOpcion("HISTORIAL");
			terminal.realizarBusqueda("114","");
			terminal.realizarBusqueda("parada","");
	}
	
	@Test
	public void testPersistenciaBusquedas() throws Exception{
		beginTransaction();
			inicializarBusquedas();			
			terminal.getHistorialBusquedas().stream().forEach(busqueda->persist(busqueda)); //persisto todas las busquedas
			commitTransaction();
		em.clear();
		
		Busqueda busquedaBD = (Busqueda) em.createQuery("from Busqueda where frase_buscada = :frase")
							.setParameter("frase", "114").getSingleResult();
		
		assertEquals(busquedaBD.getCantidadResultados(),3,0);	//3 paradas del 114
		assertFalse(busquedaBD.getFecha()==new LocalDate(2012,2,10));	//la busqueda fue hoy, no puede ser del 2012 (?
		assertEquals(busquedaBD.getResultados().get(0).getNombre(),parada1.getNombre());	
		assertEquals(busquedaBD.getResultados().get(1).getNombre(),parada2.getNombre());	
		
		em.clear();
		@SuppressWarnings("unchecked")
		List<Busqueda> busquedas = (List<Busqueda>) em.createQuery("from Busqueda").getResultList();
		assertEquals(busquedas.size(),2);			//"parada" y "114"
		assertEquals(busquedas.get(0).getFraseBuscada(),"114 ");
		assertEquals(busquedas.get(1).getFraseBuscada(),"parada ");
	}
	
	@Test
	public void testPersistirTerminal() throws Exception{		
		beginTransaction();
		Administrador admin = new Administrador();
		admin.setEmail("ASD");
		Terminal terminal= new Terminal();
			terminal.setAdministrador(admin);
			terminal.setNombre("prueba");
			terminal.setAdministrador(admin);
		Coordenadas coordenada=new Coordenadas();
			coordenada.setLatitud(99);
			coordenada.setLongitud(2);
		terminal.setCoordenadas(coordenada);
		FuncionesExtra fe=new FuncionesExtra(20);
		terminal.setExtra(fe);
		terminal.activarOpcion("MAIL");
		terminal.desactivarOpcion("HISTORIAL");
		persist(terminal);
		commitTransaction();
		em.clear();
		
		Terminal terminalBuscada = (Terminal) em.createQuery("from Terminal where Nombre = :nombre")
				.setParameter("nombre", "prueba").getSingleResult();
		assertEquals(terminalBuscada.getNombre(),"prueba");
		assertEquals(terminalBuscada.getCoordenadas().getLatitud(),99,0);
		assertEquals(terminalBuscada.getCoordenadas().getLongitud(),2,0);
		assertEquals(terminalBuscada.getExtra().getTiempoMax(),20,0);
		assertTrue(terminal.estaActivado("MAIL"));
		assertFalse(terminal.estaActivado("HISTORIAL"));
		
	}
	
	@Test
	public void TestPersistenciaReportes() throws Exception{
		beginTransaction();
		inicializarBusquedas();
		terminal.realizarBusqueda("parada", "");		
		terminal.getHistorialBusquedas().get(2).setFecha(new LocalDate(1995,10,25));
		Reporte repFechas = terminal.reporteFechas();
		Reporte repTotalResultados=terminal.reporteTotalResultados();
		Reporte repParciales=terminal.reporteResultadosParciales();
		int repFechasAntes = repFechas.getDatos().size();
		int totalResultadosAntes = repTotalResultados.getDatos().size();
		int parcialesAntes= repParciales.getDatos().size();
		em.persist(repFechas);
		em.persist(repTotalResultados);
		em.persist(repParciales);
		commitTransaction();
		em.clear();
		Reporte reporteFechasBD = (Reporte) em.createQuery("from Reporte where tipoReporte = :tipo")
							.setParameter("tipo", "Reporte Fechas")
							.getSingleResult();
		assertEquals(reporteFechasBD.getDatos().size(), repFechasAntes);
		em.clear();
		Reporte reporteTotalResultadosBD = (Reporte) em.createQuery("from Reporte where tipoReporte = :tipo")
				.setParameter("tipo", "Total Resultados")
				.getSingleResult();
		
		assertNotEquals(reporteTotalResultadosBD.getDatos().size(), totalResultadosAntes+1);
		assertEquals(reporteTotalResultadosBD.getDatos().size(), totalResultadosAntes);
		em.clear();
		Reporte reporteParcialesBD = (Reporte) em.createQuery("from Reporte where tipoReporte = :tipo")
									.setParameter("tipo", "Resultados Parciales")
									.getSingleResult();
		assertEquals(reporteParcialesBD.getDatos().size(), parcialesAntes);
	}

}
