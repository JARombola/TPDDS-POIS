package procesos;

import terminales.Terminal;

public class ExcepcionFallo extends RuntimeException {
	private Terminal terminal;
	
	public ExcepcionFallo(Terminal terminalFallo){
		terminal=terminalFallo;
	}

	public Terminal getTerminal() {
		return terminal;
	}
	
	

}
