package json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import tipos.Banco;

public class JsonFactory {

	private ObjectMapper objectMapper;

	public JsonFactory() {
		this.objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	
	public List<Banco> fromJson(String json) {
		try {
			return this.objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Banco.class));
					}catch (IOException e) {

						throw new RuntimeException("Error reading a json", e);
		}
		
	}
	
}

	

	
