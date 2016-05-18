package tests;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import externos.BancoExterno;
import json.JsonFactory;


public class testJackson {
	
		private JsonFactory jsonFactory = new JsonFactory();
		private BancoExterno bancoext;
		private String jsonBanco =  "{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\", "
				+ "\"servicios\":[\"cobro cheques\", \"depósitos\", \"extracciones\", \"transferencias\", \"créditos\", \"\", \"\", \"\" ]"
				+ "}";
			
		@Before
		public void setUp() {
			bancoext = jsonFactory.fromJson(jsonBanco,BancoExterno.class);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			assertEquals(bancoext.getNombre(), "Banco de la Plaza");
			assertEquals(bancoext.getGerente(),"Javier Loeschbor");
			assertEquals(bancoext.getSucursal(),"Avellaneda");
			assertEquals(bancoext.getServicios().get(0),"cobro cheques");
			assertEquals(bancoext.getServicios().get(5),"");
		}
		
		
			
}
