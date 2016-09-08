package tiposPoi;



import org.joda.time.LocalTime;
import pois.POI;
import javax.persistence.Entity;

@Entity
public class Local extends POI{
	
	private Rubro rubro;
	
	public Local() {
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
