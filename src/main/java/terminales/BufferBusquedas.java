package terminales;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.mongodb.MongoClient;

import externos.InterfazBuscadores;
import pois.POI;


public class BufferBusquedas {
	
	private List<POI> resultados;
	private List<InterfazBuscadores> buscadoresComponentes;
	private Datastore store;
	private Config config;
	private RedissonClient redisson;

	public BufferBusquedas(){
		Morphia morphia = new Morphia();
		morphia.mapPackage("terminales");
		morphia.mapPackage("externos");
		morphia.mapPackage("pois");
		MongoClient mongo = new MongoClient();
		store = morphia.createDatastore(mongo, "CACHE");
		resultados=new ArrayList<POI>();
		buscadoresComponentes= new ArrayList<InterfazBuscadores>();
		config = new Config(); 
		config.setUseLinuxNativeEpoll(false);
		config.useSingleServer().setAddress("127.0.0.1:6379"); 
		redisson = Redisson.create(config);
	}
	
	public List<POI> buscar(String texto1, String texto2){	
		
		RMap<String, List<POI>> map = redisson.getMap("cache");
		List<POI> resultadosCache = map.get(texto1);
		
		if(resultadosCache == null){			//Si ninguno sirvió => consulta a los externos
			List<POI> resultadosEnBuffer = new ArrayList<POI>();
			buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
			getBuscadoresComponentes().forEach(componente -> {
				resultadosEnBuffer.addAll(componente.getResultado());
				resultados.addAll(componente.getResultado());			//Guardo los del externo
				}
			);
			map.put(texto1, resultadosEnBuffer);
			return resultadosEnBuffer;
		} else {
			return resultadosCache;
		}
	}

	public void borrarBusquedaCache(String frase){
		RMap<String, List<POI>> map = redisson.getMap("cache");
		map.delete();
		
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
