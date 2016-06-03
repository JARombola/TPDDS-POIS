package principal;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class ControlTerminales {
	private List<Terminal> terminales;
	private List<LocalDate> fechas;
	
	public ControlTerminales(){
		terminales=new ArrayList<Terminal>();
		fechas=new ArrayList<LocalDate>();
	}
	
	private void agregarFecha(LocalDate fecha){
		fechas.add(fecha);
	}
	
	public void busquedasParcialesPorTerminal(Terminal unaTerminal){
			System.out.println("Usuario: "+unaTerminal.getNombre());
			System.out.println("Cantidad de Resultados Parciales");
			unaTerminal.getHistorialBusquedas().forEach(busqueda->System.out.println(busqueda.cantidadResultados));
	}
	public void busquedasTotalesDeTerminales(){
		System.out.println("Usuario     |   Cantidad de Resultados Totales");
		terminales.forEach(terminal-> System.out.println(terminal.getNombre() + "   |   " + terminal.cantidadTotalResultados()));
	}
}
