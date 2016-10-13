package terminales;

import java.util.ArrayList;
import java.util.List;



public class ControlTerminales {
	private static ControlTerminales instancia;
	private List<Terminal> terminales;
	

	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
	}
	
	public static ControlTerminales getInstancia(){
		if(instancia==null)
			instancia = new ControlTerminales();
		return instancia;
	}

	public Reporte busquedasParcialesPorTerminal(Terminal unaTerminal){
//		TODO: Este método y el de abajo tienen el code smell de middle man. - Aldana
		Reporte busquedasParciales=unaTerminal.reporteResultadosParciales();
		return busquedasParciales;
	}
	
	public Reporte totalDeResultadosPorTerminal(Terminal unaTerminal){
		Reporte reporteTotalResultados=unaTerminal.reporteTotalResultados();
		return reporteTotalResultados;
	}
	
	
	public List<Terminal> getTerminales() {
		return terminales;
	}
	
	public void agregarTerminal(Terminal terminal) {
//		TODO: Dónde persisten las terminales? - Aldana.
		this.terminales.add(terminal);
	}

}
