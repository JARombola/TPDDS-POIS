package procesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import pois.POI;
import terminales.Mapa;

public class ActualizacionLocales extends Proceso{
	private String ruta;
	
	
	public ActualizacionLocales(String  ruta){
		this.ruta = ruta;	
	}
	

	public int ejecutar() throws IOException{
		BufferedReader archivo = null;
			try {
				archivo = new BufferedReader(new FileReader(ruta));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		String sCurrentLine;
		int afectados=0;
			Mapa mapa=Mapa.getInstancia();
			while ((sCurrentLine = archivo.readLine()) != null) {
				String[] parts = sCurrentLine.split(";");
				String nombre = parts[0];
					POI poi = mapa.getPOI(nombre);
					poi.eliminarTags();
					for (int i=1; i<parts.length;i++){
						poi.agregarTag(parts[i]);
					}
					mapa.agregarOmodificar(poi);
			afectados++;
			}
			archivo.close();
		return afectados;
	}
}

