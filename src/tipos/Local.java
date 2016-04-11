package tipos;

import principal.POI;

public class Local extends POI{
	private Rubro rubro;
	private double radioCercania;
	
	public Local(){
		this.radioCercania = rubro.getRadioCercania();
	}
	
	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public double getRadioCercania() {
		return radioCercania;
	}

	public void setRadioCercania(double radioCercania) {
		this.radioCercania = radioCercania;
	}

	
	
	
	
}
