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
	}
}
