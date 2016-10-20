package terminales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import externos.InterfazBuscadores;
import pois.POI;
import tiposPoi.Banco;
import tiposPoi.CGP;


public class BufferBusquedas {
	
	private List<POI> resultados;
	private List<InterfazBuscadores> buscadoresComponentes;
	private Config config;
	private RedissonClient redisson;
	private RMap<String, List<String>> map;
	private String NOMBRE_REDIS_CACHE = "cache";
	
	public BufferBusquedas(){
		String IP_PUERTO_MONGO = "127.0.0.1:6379";
		resultados=new ArrayList<POI>();
		buscadoresComponentes= new ArrayList<InterfazBuscadores>();
		config = new Config(); 
		config.setUseLinuxNativeEpoll(false);
		config.useSingleServer().setAddress(IP_PUERTO_MONGO); 
		redisson = Redisson.create(config);
		map = redisson.getMap(NOMBRE_REDIS_CACHE);
	}
	
	public List<POI> buscar(String texto1, String texto2){	
		List<String> resultadosCacheJson = map.get(texto1);
		if (resultadosCacheJson!=null){
			List<POI> resultadosCache = poisFromJson(resultadosCacheJson);
			return resultadosCache;
		}
		else{			//Si ninguno sirvió => consulta a los externos
			List<POI> resultadosEnBuffer = new ArrayList<POI>();
			buscadoresComponentes.forEach(componente -> componente.buscar(texto1, texto2));
			getBuscadoresComponentes().forEach(componente -> {
				resultadosEnBuffer.addAll(componente.getResultado());
				resultados.addAll(componente.getResultado());			//Guardo los del externo
				}
			);
			
			map.put(texto1, poisToJson(resultadosEnBuffer));
			return resultadosEnBuffer;
		}
	}
	
	private List<POI> poisFromJson(List<String> listaPoisString){
		List<POI> pois = new ArrayList<POI>();
		
		listaPoisString.stream().forEach(poiString->{
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JodaModule());
			POI poiFromString = null;
			try {
				poiFromString = (POI) mapper.readValue(poiString, Banco.class);
			} catch (IOException e) {		//Si no es un Banco es un CGP, no hay otra opcion
				try {
					poiFromString = (POI) mapper.readValue(poiString, CGP.class);
				} catch (IOException e1) {
			//		e1.printStackTrace();			ACA nunca va a entrar... pero Java me obliga a poner el Try/catch
				}
			//		e1.printStackTrace();			Lo mismo que arriba...
			}
			pois.add(poiFromString);
		});
	return pois;
	
	}
	
	private List<String> poisToJson(List<POI> resultados){
	List<String> listaPoisString = new ArrayList<String>();
		resultados.stream().forEach(poi->{
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JodaModule());
			String poiString = new String();
			try {
				poiString = mapper.writeValueAsString(poi);
				listaPoisString.add(poiString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	return listaPoisString;
	
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
