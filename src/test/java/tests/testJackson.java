package tests;


import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import json.JsonFactory;
import tipos.Banco;


public class testJackson {
	
		private JsonFactory jsonFactory = new JsonFactory();
		private Banco banco;
		private String jsonBanco =  "{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\"}";
			
		@Before
		public void setUp() {
			banco = jsonFactory.fromJson(jsonBanco,Banco.class);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			assertEquals(banco.getNombre(), "Banco de la Plaza");
			assertEquals(banco.getGerente(),"Javier Loeschbor");
			assertEquals(banco.getSucursal(),"Avellaneda");
		}
		
		
			}

