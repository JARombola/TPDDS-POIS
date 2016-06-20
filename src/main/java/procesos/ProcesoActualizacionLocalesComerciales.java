package procesos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import principal.POIS.Horario;
import principal.POIS.POI;
import principal.Terminales.Mapa;

public class ProcesoActualizacionLocalesComerciales implements Proceso{
	BufferedReader  archivo;
	Mapa mapa;
	public ProcesoActualizacionLocalesComerciales(String  ruta){
		try{
			this.archivo=new  BufferedReader(new FileReader(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void ejecutar() {
		// TODO Auto-generated method stub
		
		String sCurrentLine;
		try{
			while ((sCurrentLine = archivo.readLine()) != null) {
				String[] parts = sCurrentLine.split(";");
				String nombre = parts[0]; 
				POI poi=mapa.getPOI(nombre);
				if(poi!=null){
					boolean primero=true;
					List<String>tags=poi.getTags();
					//elimino los que no estan 
					for (String tag : tags){ 
						boolean existe=parts.equals(tag);
						if(!existe){//no esta
							poi.eliminarTag(tag);
						}
					}
					//agrego los nuevos tags
					for (String tag : parts){ 
						if(!primero){//salteo el primero porque es el nombre
							int index=tags.indexOf(tag);
							if(index<0){//no esta
								poi.agregarTag(tag);
							}
						}
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void setFecha(Horario dia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHorario(Horario horario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMapa(Mapa mapa) {
		// TODO Auto-generated method stub
		this.mapa=mapa;
	}

}