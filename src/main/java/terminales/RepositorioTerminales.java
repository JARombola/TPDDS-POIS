package terminales;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;



public class RepositorioTerminales implements WithGlobalEntityManager{
	private static RepositorioTerminales instancia;
	private List<Terminal> terminales;
	

	public RepositorioTerminales(){
		terminales=new ArrayList<Terminal>();
	}
	
	public static RepositorioTerminales getInstancia(){
		if(instancia==null)
			instancia = new RepositorioTerminales();
		return instancia;
	}

	public Reporte totalDeResultadosPorTerminal(Terminal unaTerminal){
		Reporte reporteTotalResultados=unaTerminal.reporteTotalResultados();
		return reporteTotalResultados;
	}
	
	
	public List<Terminal> getTerminales() {
		return terminales;
	}
	
	public void actualizarTerminal(Terminal terminal){
			
	}
	
	public void agregarTerminal(Terminal terminal) {
//		TODO: Dónde persisten las terminales? - Aldana.
		if(!entityManager().getTransaction().isActive()) entityManager().getTransaction().begin();
		entityManager().persist(terminal);
		entityManager().getTransaction().commit();
		this.terminales.add(terminal);
		
	}

}
