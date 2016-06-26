package principal.Terminales;

import java.util.ArrayList;
import java.util.List;


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
}
