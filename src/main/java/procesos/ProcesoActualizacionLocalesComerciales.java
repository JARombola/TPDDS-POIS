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
//			TODO: Mejor guarden la ruta y cuando tengan que ejecutar el proceso abran el archivo.
//			Piensen que no deberían dejar un archivo abierto hasta que se ejecute el proceso. - Aldana
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
//					TODO: Este código está un poco complejo y difícil de leer.
//					Para empezar podrían mejorarlo sacando el try/catch a la lógica del proceso
//					ya que la lógica está repetida.
//					Luego, podrían eliminar el if dentro del for 
//					una idea es reemplazándolo por un for que vaya de i = 1
//					hasta el length de 'parts' - Aldana
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
//			TODO: este catch no debería estar, ya que ante cualquier excepción, al menos en principio, 
//			se debería disparar el manejo de error del proceso - Aldana
			e.printStackTrace();
		}
		return resultado;
	}
}
