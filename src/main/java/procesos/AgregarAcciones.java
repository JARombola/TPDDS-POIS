package procesos;

import java.util.List;
import java.util.stream.Collectors;


import pois.Comuna;
import terminales.ControlTerminales;
import terminales.Terminal;

public class AgregarAcciones extends Proceso{

	private Comuna comuna;
	private Terminal terminal=null;
	private String accion;					//las terminales usan strings para activar/desactivar acciones
	private boolean todos;
	
	
	public AgregarAcciones(String accion) {
		setAccion(accion);		
	}

	public void AgregarAccionComuna(Comuna unaComuna) {
		setComuna(unaComuna);
	}
	
	public void agregarAccionTerminal(Terminal unaTerminal) {
		setTerminal(unaTerminal);
	}
	
	public void agregarAccionTodasTerminales() {
		setTodos(true);
	}

	public int ejecutar(){		
		
		if(terminal!=null){
			terminal.activarOpcion(getAccion()); 
			return 1;
		}
		
		ControlTerminales centralTerminales = ControlTerminales.getInstancia();
		
		if (isTodos()) return activarOpcionTodasTerminales(centralTerminales);
		
		if (getComuna()!=null) return activarOpcionComuna(centralTerminales);
		
		return 0;
	}
	
	private int activarOpcionTodasTerminales(ControlTerminales centralTerminales){
		centralTerminales.getTerminales()
			.stream()
			.forEach(terminal->terminal.activarOpcion(getAccion()));
		return centralTerminales.getTerminales().size();	
	}
	
	private int activarOpcionComuna(ControlTerminales centralTerminales){
		List<Terminal> terminales = centralTerminales.getTerminales().stream()
					.filter(unaTerminal->unaTerminal.estaEnLaComuna(comuna))
					.collect(Collectors.toList());
		terminales.stream().forEach(terminal->terminal.activarOpcion(getAccion()));
		return terminales.size();
	}

	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	

}