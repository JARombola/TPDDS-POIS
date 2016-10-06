package procesos;

import terminales.Terminal;


@SuppressWarnings("serial")
public class ExcepcionFalloConfiguracion extends RuntimeException {
	private Terminal terminal;
	
	public ExcepcionFalloConfiguracion(Terminal terminalFallo){
		terminal=terminalFallo;
	}

	public Terminal getTerminal() {
		return terminal;
	}
	
	

}
