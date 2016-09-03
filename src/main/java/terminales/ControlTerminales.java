package terminales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pois.Comuna;


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

	public int setearOpcion(String opcionActivar) {
		getTerminales().stream().forEach(terminal->terminal.activarOpcion(opcionActivar));
		return getTerminales().size();
	}

	public int setearOpcion(Comuna comuna, String opcionActivar) {
		List<Terminal>terminales=getTerminales().stream()
						.filter(unaTerminal->unaTerminal.estaEnLaComuna(comuna))
						.collect(Collectors.toList());
		terminales.forEach(unaTerminal->unaTerminal.activarOpcion(opcionActivar));
		return terminales.size();
	}

	public int setearOpcion(Terminal terminal, String opcionActivar) {
		List<Terminal>terminales= getTerminales().stream()
									.filter(unaTerminal->unaTerminal.equals(terminal))
									.collect(Collectors.toList());
		terminales.forEach(unaTerminal->unaTerminal.activarOpcion(opcionActivar));
		return terminales.size();
	}
}
