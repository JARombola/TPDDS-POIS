package pois;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

@Entity
public class Comuna {
	
	@Id @GeneratedValue
	private int id;
	
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Transient
	Polygon polygonoActual;
	
	public Comuna(){
		 this.polygonoActual = new Polygon();
	}
	
	public void addPunto(Coordenadas coordenada){
		Point punto=new Point(coordenada.getLatitud(),coordenada.getLongitud());
        this.polygonoActual.add(punto);
	}
	
	public boolean dentroDeLaZona(Coordenadas coordenada){
		Point punto=new Point(coordenada.getLatitud(),coordenada.getLongitud());
		return polygonoActual.isInside(punto);
	}

}
