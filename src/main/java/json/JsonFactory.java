package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

	


	public Banco fromJson(String json, Class<Banco> ba) {
		try {
			return this.objectMapper.readValue(json, ba);
		} catch (IOException e) {

			throw new RuntimeException("Error reading a json", e);
		}
}
}
	
