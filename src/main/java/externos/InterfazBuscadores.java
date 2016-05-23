package externos;

import java.util.List;

import principal.POI;

public interface InterfazBuscadores {
	
	public List<POI> getResultado();
	
	public void buscar(String texto1, String texto2);
}
