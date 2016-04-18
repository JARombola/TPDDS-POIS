package principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;




public class Mapa {

	List<POI> pois;

	public Mapa() {
		pois = new ArrayList<POI>();
	}

	public List<POI> getPOI() {
		return pois;
	}

	public void setPOI(POI poi) {
		this.pois.add(poi);
	}

	public void agregarPOI(POI unPOI) {
		this.pois.add(unPOI);
	}

	public void Buscar(String texto) {
		/*List<String> namesList = pois.stream().map(POI::getNombre).collect(Collectors.toList());
		namesList.removeIf((s ->!s.toLowerCase().contains(texto.toLowerCase())));
		System.out.println(Arrays.toString(namesList.toArray()));

		return namesList;
	*/
		getPOI().stream()
		.forEach(poi->poi.tienePalabra(texto));
	}
}
