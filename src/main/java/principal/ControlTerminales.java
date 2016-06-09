package principal;

import java.util.ArrayList;
import java.util.List;


public class ControlTerminales {
	private List<Terminal> terminales;
	

	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
	}

	public int busquedasParcialesPorTerminal(Terminal unaTerminal){
		//	System.out.println("Usuario: "+unaTerminal.getNombre());
		//	System.out.println("Resultados Parciales");
		//	unaTerminal.getHistorialBusquedas().forEach(busqueda->System.out.println(busqueda.cantidadResultados));
		//	System.out.println("_____________________");
			int cantidad=unaTerminal.getHistorialBusquedas().size();
			return cantidad;
	}
	public int busquedasTotalesDeTerminales(){
		System.out.println("Usuario     |   Cantidad de Resultados Totales");
		terminales.forEach(terminal-> System.out.println(terminal.getNombre() + "   |   " + terminal.cantidadTotalResultados()));
		int cantidadResultados=terminales.stream().mapToInt(a->a.cantidadTotalResultados()).sum();
		return cantidadResultados;
	}
	public List<Terminal> getTerminales() {
		return terminales;
	}
	
	public void agregarTerminal(Terminal terminal) {
		this.terminales.add(terminal);
	}
}
