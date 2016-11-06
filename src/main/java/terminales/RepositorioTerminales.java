package terminales;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioTerminales implements WithGlobalEntityManager, TransactionalOps{
	private static RepositorioTerminales instancia;

	public RepositorioTerminales(){
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
	
	
	
	public void actualizar(Terminal terminal){
		withTransaction(() ->{
			entityManager().persist(terminal);
//		entityManager().getTransaction().commit();
		});
		//commitTransaction();
	}
	
	@SuppressWarnings("unchecked")
	public List<Terminal> getTerminales(){
		List<Terminal> terminales = entityManager().createQuery("from Terminal").getResultList();
		return terminales;
	}

	@SuppressWarnings("unchecked")
	public Terminal getTerminal(int id){
		Terminal terminal = entityManager().find(Terminal.class,id);
		return terminal;
	}
	
	
	
	public void eliminarTerminal(int id){
		Terminal eliminar = entityManager().find(Terminal.class, id);
		withTransaction(() ->{
			entityManager().remove(eliminar);
		});
	}
	
	@SuppressWarnings("unchecked")
	public void eliminarTerminales(){
		List<Terminal> terminales =(List<Terminal>) entityManager().createQuery("from Terminal").getResultList();
		terminales.stream().forEach(t->eliminarTerminal(t.getId()));
	}
	

}
