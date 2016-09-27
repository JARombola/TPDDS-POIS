package pois;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

@org.mongodb.morphia.annotations.Embedded
@Entity
public class Comuna {
	
	@org.mongodb.morphia.annotations.Transient
	@Id @GeneratedValue
	private int id;
	
	private String nombre;
	

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

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
