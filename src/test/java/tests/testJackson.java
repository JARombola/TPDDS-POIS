package tests;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import json.JsonFactory;
import principal.POI;
import tipos.Banco;


public class testJackson {
	
		private JsonFactory jsonFactory = new JsonFactory();
		private List<Banco> banco;
		private String jsonBanco =  "[{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\"}]";
				//+ "\"servicios\": [\"cobro cheques\", \"depósitos\", \"extracciones\", \"transferencias\", \"créditos\"]}"; //no compila con esta lista :(
		
		
		@Before
		public void setUp() {
			banco = jsonFactory.fromJson(jsonBanco);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			assertEquals(banco.get(0).getNombre(), "Banco de la Plaza");
			assertEquals(banco.get(0).getGerente(),"Javier Loeschbor");
			assertEquals(banco.get(0).getSucursal(),"Avellaneda");
			}
		
	
			}

