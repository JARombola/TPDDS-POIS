package procesos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pois.POI;
import terminales.ControlTerminales;
import terminales.Terminal;

public class ProcesoActualizacionLocalesComerciales extends Proceso{
	private BufferedReader archivo;
	
	
	public ProcesoActualizacionLocalesComerciales(String  ruta, ControlProcesos control, ControlTerminales terminales){
		super(control,terminales);
		try{
			this.archivo=new BufferedReader(new FileReader(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	

	public int ejecutarProceso(){
		
		String sCurrentLine;
		int resultado=0;
		try {
			while ((sCurrentLine = archivo.readLine()) != null) {
				String[] parts = sCurrentLine.split(";");
				String nombre = parts[0]; 
				
				
				List<Terminal> terminalesActualizar= getCentralTerminales().getTerminales()
				.stream()
				.filter(unaTerminal->unaTerminal.getMapa().getPOI(nombre)!=null)
				.collect(Collectors.toList());
				
				resultado+=terminalesActualizar.size();
				
				terminalesActualizar.forEach(unaTerminal->{
					try {
						POI poi=unaTerminal.getMapa().getPOI(nombre);
						boolean primero=true;
							poi.eliminarTags();
							for (String tag : parts){ 
								if(!primero){//salteo el primero porque es el nombre
									poi.agregarTag(tag);
								}else{
									primero=false;
								}
							}
					} catch (Exception e) {
						throw new ExcepcionFallo(unaTerminal);
					}
				}	
			);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
}
