package terminales;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import externos.InterfazBuscadores;
import pois.POI;

public class BufferBusquedas {
	
	private List<POI> resultados;
	private List<InterfazBuscadores> buscadoresComponentes;
	private Datastore store;

	public BufferBusquedas(){
		Morphia morphia = new Morphia();
		morphia.mapPackage("terminales");
		morphia.mapPackage("externos");
		morphia.mapPackage("pois");
		MongoClient mongo = new MongoClient();
		store = morphia.createDatastore(mongo, "CACHE");
		resultados=new ArrayList<POI>();
		buscadoresComponentes= new ArrayList<InterfazBuscadores>();
	}
	
	public List<POI> buscar(String texto1, String texto2){		//Asincronico, entonces...
		
		Query<POI> query = store.createQuery(POI.class); 
			query.or(query.criteria("direccion.calle").equal(texto1),
					 query.criteria("direccion.localidad").equal(texto1)); 
			
		List<POI> resultadosEnBuffer = query.asList();
			
		if(resultadosEnBuffer.isEmpty()){			//Si ninguno sirvió => consulta a los externos
			buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
			getBuscadoresComponentes().forEach(componente -> {
				resultadosEnBuffer.addAll(componente.getResultado());
				resultados.addAll(componente.getResultado());			//Guardo los del externo
				}
			);
		}
		return resultadosEnBuffer;
	}

	public void agregarExterno(InterfazBuscadores componente) {
		this.buscadoresComponentes.add(componente);
	}
	
	public List<InterfazBuscadores> getBuscadoresComponentes() {
		return buscadoresComponentes;
	}

	public List<POI> getResultados() {
		return resultados;
	}

	public void setResultados(List<POI> resultados) {
		this.resultados = resultados;
	}

	public void setBuscadoresComponentes(List<InterfazBuscadores> buscadoresComponentes) {
		this.buscadoresComponentes = buscadoresComponentes;
	}
}
