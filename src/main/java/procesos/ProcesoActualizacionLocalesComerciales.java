package procesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import pois.POI;
import terminales.Mapa;

public class ProcesoActualizacionLocalesComerciales extends Proceso{
	private String ruta;
	
	
	public ProcesoActualizacionLocalesComerciales(String  ruta){
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
			Mapa mapa=Mapa.getInstance();
			while ((sCurrentLine = archivo.readLine()) != null) {
				String[] parts = sCurrentLine.split(";");
				String nombre = parts[0];
					POI poi = mapa.getPOI(nombre);
					poi.eliminarTags();
					for (int i=1; i<parts.length;i++){
						poi.agregarTag(parts[i]);
					}
			afectados++;
			}
			archivo.close();
		return afectados;
	}
}
//					TODO: Este código está un poco complejo y difícil de leer.
//					Para empezar podrían mejorarlo sacando el try/catch a la lógica del proceso
//					ya que la lógica está repetida.
//					Luego, podrían eliminar el if dentro del for 
//					una idea es reemplazándolo por un for que vaya de i = 1
//					hasta el length de 'parts' - Aldana
