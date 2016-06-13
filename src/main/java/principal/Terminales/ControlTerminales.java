package principal.Terminales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ControlTerminales {
	private List<Terminal> terminales;
	

	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
	}

	public int busquedasParcialesPorTerminal(Terminal unaTerminal){
		List<String> busquedasParciales=new ArrayList<String>();
		busquedasParciales.add("Terminal: "+unaTerminal.getNombre());	//Podria hacer esto el buscador, mas cohesivo 
		busquedasParciales.add("Resultados Parciales: ");
		busquedasParciales.addAll(unaTerminal.historialBusquedas().stream()
									.map(busqueda->String.valueOf(busqueda.cantidadResultados))
									.collect(Collectors.toList()));
		
		//busquedasParciales.forEach(a->System.out.println(a));			//Para prueba, Aldana no nos mates (?
			int cantidad=unaTerminal.historialBusquedas().size();
			return cantidad;
	}
	
	public int busquedasTotalesDeTerminales(){
		List<String> busquedasTotales=new ArrayList<String>();	//Crea un List<String> con todos los datos (Podria ser una nueva Clase "Reporte"
		busquedasTotales.add("Usuario     |   Cantidad de Resultados Totales");
		getTerminales().forEach(terminal->{
			busquedasTotales.add(terminal.getNombre() + "   |   " + String.valueOf(terminal.cantidadTotalResultados()));			
			}
		);	
		//busquedasTotales.forEach(a->System.out.println(a));
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
