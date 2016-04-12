package tipos;

import principal.POI;

public class Local extends POI{
	private Rubro rubro;
	
	public Local(){
	}
	
	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public double getRadioCercania() {
		return rubro.getRadioCercania();
	}


	
	
	
	
}
