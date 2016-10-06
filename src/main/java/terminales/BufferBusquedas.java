package terminales;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import externos.InterfazBuscadores;
import pois.POI;
import tiposPoi.ParadaColectivo;

public class BufferBusquedas {
	Set<POI> resultados;
	List<InterfazBuscadores> buscadoresComponentes;
	Morphia morphia;
	MongoClient mongo;
	Datastore store;

	public BufferBusquedas(){
		resultados=new HashSet<POI>();
		buscadoresComponentes= new ArrayList<InterfazBuscadores>();
		morphia = new Morphia();
		mongo = new MongoClient();
		morphia.mapPackage("terminales");
		store = morphia.createDatastore(mongo, "BasePOIS");
	}
	
	public List<POI> buscar(String texto1, String texto2){		//Asincronico, entonces...
		Query<POI> query = store.find(POI.class); 
		query.or(query.criteria("direccion.calle").equal(texto1), query.criteria("direccion.localidad").equal(texto1)); 
		List<POI> resultadosEnBuffer = query.asList();
		
		System.out.println(query.toString());
		
			
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

	
	
}
