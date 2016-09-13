package tiposPoi;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;
import pois.POI;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Local extends POI{
	
	@OneToOne @Cascade(value=CascadeType.ALL)
	private Rubro rubro;
	
	public Local() {
		super();
	}

	//---------------BUSQUEDA-----------------------------------
	public boolean tienePalabra(String texto){
		 return (super.tienePalabra(texto) || tienePalabraEnRubro(texto));
	}
		 
	public boolean tienePalabraEnRubro(String texto){
		return getRubro().tienePalabra(texto);
	}
	
	// -------------------GETTERS,SETTERS-----------------
	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public double getRadioCercania() {
		return rubro.getRadioCercania();
	}
	
	public boolean estaDisponible(int dia, LocalTime hora,String palabra){
		return getHorarios().estaDisponible(dia,hora);
	}
}
