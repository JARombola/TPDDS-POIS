package procesos;

import configuracionTerminales.Administrador;
import principal.POIS.Comuna;
import principal.Terminales.ControlTerminales;
import principal.Terminales.Terminal;

public class ProcesoAgregarAccionesParaUsuarios extends Proceso{

	private ControlTerminales centralTerminales;
	private Comuna comuna;
	private Terminal terminal;
	private String accion;					//las terminales usan strings para activar/desactivar acciones
	private boolean todos;
	
	
	public ProcesoAgregarAccionesParaUsuarios(ControlTerminales central, Administrador administrador, String accion) {
		super(administrador);
		setCentralTerminales(central);
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

	public int ejecutarProceso() {		
		int resultados = 0;
		
		if(isTodos()){
			resultados=getCentralTerminales().setearOpcion(getAccion());}
		
		if(getComuna()!=null){
			resultados=getCentralTerminales().setearOpcion(getComuna(),getAccion());}
		
		if(getTerminal()!=null){
			resultados=getCentralTerminales().setearOpcion(getTerminal(),getAccion());}
		
		return resultados;
	}

	
	public ControlTerminales getCentralTerminales() {
		return centralTerminales;
	}

	public void setCentralTerminales(ControlTerminales centralTerminales) {
		this.centralTerminales = centralTerminales;
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
