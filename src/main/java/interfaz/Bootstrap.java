package interfaz;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import configuracionTerminales.Administrador;
import pois.Direccion;
import pois.POI;
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
				terminal.setNombre("TerminalPrueba");
				terminal.setPass("1234");
			persist(terminal);

			Terminal terminal2 = new Terminal();
				terminal2.setNombre("Terminal2");
				terminal2.setPass("1111");
				terminal2.activarOpcion("mail");
			persist(terminal2);
			
		});
	}
}


