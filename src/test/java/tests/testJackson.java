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

import externos.BancoExterno;
import json.JsonFactory;



public class testJackson {
	
		private JsonFactory jsonFactory = new JsonFactory();

		private List<BancoExterno> bancoext;
		private String jsonBanco =  "[{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\", "
				+ "\"servicios\":[\"cobro cheques\", \"depósitos\", \"extracciones\", \"transferencias\", \"créditos\", \"\", \"\", \"\" ]"
				+ "}]";
			
		@Before
		public void setUp() {
			bancoext = jsonFactory.fromJson(jsonBanco);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			assertEquals(bancoext.get(0).getNombre(), "Banco de la Plaza");
			assertEquals(bancoext.get(0).getGerente(),"Javier Loeschbor");
			assertEquals(bancoext.get(0).getSucursal(),"Avellaneda");
			assertEquals(bancoext.get(0).getServicios().get(0),"cobro cheques");
			assertEquals(bancoext.get(0).getServicios().get(5),"");
		}
		
}
		
		
			

