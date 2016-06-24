package procesos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

import principal.POIS.POI;
import principal.Terminales.Mapa;

public class ProcesoActualizacionLocalesComerciales extends Proceso{
	BufferedReader  archivo;
	

	
	public ProcesoActualizacionLocalesComerciales(String  ruta){
		try{
			this.archivo=new  BufferedReader(new FileReader(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	


	public void run() {
		String sCurrentLine;
		try{
			while ((sCurrentLine = archivo.readLine()) != null) {
				String[] parts = sCurrentLine.split(";");
				String nombre = parts[0]; 
				POI poi=mapa.getPOI(nombre);
				if(poi!=null){
					boolean primero=true;
					poi.eliminarTags();
					for (String tag : parts){ 
						if(!primero){//salteo el primero porque es el nombre
							poi.agregarTag(tag);
						}
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		
	}


	
	
	



}
