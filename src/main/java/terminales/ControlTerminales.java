package terminales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pois.Comuna;
import procesos.ExcepcionFallo;


public class ControlTerminales {
	private List<Terminal> terminales;
	

	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
	}

	public Reporte busquedasParcialesPorTerminal(Terminal unaTerminal){
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
		this.terminales.add(terminal);
	}

	public int setearOpcion(String opcionActivar) {
			getTerminales().stream()
			.forEach(terminal->{
			try {
				terminal.activarOpcion(opcionActivar);
			} catch (Exception e) {
					throw new ExcepcionFallo(terminal);
			}
			});
	return getTerminales().size();
	}

	public int setearOpcion(Comuna comuna, String opcionActivar){
		List<Terminal>terminales=getTerminales().stream()
						.filter(unaTerminal->unaTerminal.estaEnLaComuna(comuna))
						.collect(Collectors.toList());
		terminales.forEach(unaTerminal->{
			
			try {
				unaTerminal.activarOpcion(opcionActivar);
			} catch (Exception e) {
				throw new ExcepcionFallo(unaTerminal);
			}
		}
		);
			
		return terminales.size();
	}

	public int setearOpcion(Terminal terminal, String opcionActivar){
		List<Terminal>terminales= getTerminales().stream()
									.filter(unaTerminal->unaTerminal.equals(terminal))
									.collect(Collectors.toList());
		terminales.forEach(unaTerminal->{
			
			try {
				unaTerminal.activarOpcion(opcionActivar);
			} catch (Exception e) {
					throw new ExcepcionFallo(unaTerminal);
			}}
		);
		return terminales.size();
	}
}
