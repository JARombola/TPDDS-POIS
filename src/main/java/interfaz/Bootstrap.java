package interfaz;

import org.joda.time.LocalDate;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import configuracionTerminales.Administrador;
import pois.Direccion;
import pois.POI;
import terminales.Busqueda;
import terminales.RepositorioTerminales;
import terminales.Terminal;
import tiposPoi.Banco;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	public void init(){
		withTransaction(() ->{
			POI banco= new Banco();
				banco.setNombre("BANCO");
			Direccion dire = new Direccion();
				dire.setBarrio("Devoto");
				dire.setCalle("Beiro");
				dire.setCodigoPostal(1111);
				dire.setLocalidad("CABA");
				dire.setProvincia("BS AS");
				dire.setPais("Arg");
				banco.setDireccion(dire);
			
			persist(banco);
			
			
			POI banco2= new Banco();
				banco2.setNombre("b");
			Direccion dire2 = new Direccion();
				dire2.setBarrio("Almagro");
				dire2.setCalle("Medrano");
				dire2.setCodigoPostal(1111);
				banco2.setDireccion(dire2);
			persist(banco2);
		
			Administrador admin = new Administrador();
				admin.setEmail("admin");
				admin.setPass("admin");
			persist(admin);
			
			Terminal terminal = new Terminal();
			terminal.eliminarBusquedas();
				terminal.setNombre("TerminalPrueba");
				terminal.setPass("1234");
				terminal.setComuna("a");
			persist(terminal);
			terminal.eliminarBusquedas();

			Terminal terminal2 = new Terminal();
			terminal2.eliminarBusquedas();
				terminal2.setNombre("Terminal2");
				terminal2.setPass("1111");
				terminal2.activarOpcion("mail");
				terminal2.setComuna("b");
			persist(terminal2);
			terminal2.eliminarBusquedas();
		});
			
			Terminal terminal=RepositorioTerminales.getInstancia().getTerminal(1);
			terminal.activarOpcion("historial");
			terminal.realizarBusqueda("BANCO","");
			Terminal terminal2=RepositorioTerminales.getInstancia().getTerminal(2);
			terminal2.activarOpcion("historial");
			terminal.realizarBusqueda("b","");
			Busqueda busqueda = terminal.getHistorialBusquedas().get(0);
				busqueda.setFecha(new LocalDate("2016-11-04"));
				terminal.getOpciones().guardarBusqueda(busqueda);
				busqueda.setFecha(new LocalDate("2016-11-04"));
				terminal.getOpciones().guardarBusqueda(busqueda);
				busqueda.setFecha(new LocalDate("2016-11-04"));
				terminal.getOpciones().guardarBusqueda(busqueda);
	}
}


