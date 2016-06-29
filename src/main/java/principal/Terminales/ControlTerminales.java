package principal.Terminales;

import java.util.ArrayList;
import java.util.List;

import principal.POIS.Comuna;


public class ControlTerminales {
	private List<Terminal> terminales;
	

	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
	}

	public List<DatosReporte> busquedasParcialesPorTerminal(Terminal unaTerminal){
		List<DatosReporte> busquedasParciales=unaTerminal.obtenerResultadosParciales();
		return busquedasParciales;
	}
	
	public DatosReporte totalDeResultadosPorTerminal(Terminal unaTerminal){
		DatosReporte reporteTotalResultados=unaTerminal.cantidadTotalResultados();
		return reporteTotalResultados;
	}
	
	
	public List<Terminal> getTerminales() {
		return terminales;
	}
	
	public void agregarTerminal(Terminal terminal) {
		this.terminales.add(terminal);
	}

	public void setearOpcion(String opcionActivar) {
		getTerminales().stream()
						.forEach(terminal->terminal.activarOpcion(opcionActivar));
		
	}

	public void setearOpcion(Comuna comuna, String opcionActivar) {
		getTerminales().stream()
						.filter(unaTerminal->unaTerminal.estaEnLaComuna(comuna))
						.forEach(unaTerminal->unaTerminal.activarOpcion(opcionActivar));
		
	}

	public void setearOpcion(Terminal terminal, String opcionActivar) {
		getTerminales().stream().
						filter(unaTerminal->unaTerminal.equals(terminal))
						.forEach(unaTerminal->unaTerminal.activarOpcion(opcionActivar));
	}
}
