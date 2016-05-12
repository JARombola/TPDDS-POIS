package externos;

import java.util.ArrayList;
import java.util.List;

public class OrigenDatos {
	public List<CentroDTO> buscar(String palabra){
		List<CentroDTO>lista=new ArrayList<CentroDTO>();
		CentroDTO p=new CentroDTO();p.setDomicilio("AAA");
		CentroDTO q=new CentroDTO();q.setDomicilio("BBB");
		lista.add(p);
		lista.add(q);
		return lista;
	}
	public String buscar(String banco, String servicio){
		return "Encontrado";
	}
}
