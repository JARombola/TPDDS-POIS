package externos;

import java.util.List;

import principal.POIS.POI;

public interface InterfazBuscadores {
	
	public List<POI> getResultado();
	
	public void buscar(String texto1, String texto2);
}
