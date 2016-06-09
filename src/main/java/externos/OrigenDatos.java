package externos;

import java.util.List;

public interface OrigenDatos {
	public List<CentroDTO> buscar(String palabra);
	
	public String buscar(String banco, String servicio);
	
}
