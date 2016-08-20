package procesos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import configuracionTerminales.Administrador;
import principal.POIS.POI;
import principal.Terminales.Mapa;

public class ProcesoActualizacionLocalesComerciales extends Proceso{
	BufferedReader  archivo;
	Mapa mapa;
	
	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}
	
	public ProcesoActualizacionLocalesComerciales(String  ruta, Administrador admin){
		super(admin);
		try{
			this.archivo=new BufferedReader(new FileReader(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	

	public int ejecutarProceso() throws Exception  {
		
		String sCurrentLine;
		int resultado=0;
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
						resultado ++;
					}else{
						primero=false;
					}
				}
			}
		}

		return resultado;
	}



}
