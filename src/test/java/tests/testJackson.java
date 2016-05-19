package tests;


import static org.junit.Assert.*;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.invocation.realmethod.CGLIBProxyRealMethod;

import json.JsonFactory;
import principal.Direccion;
import principal.Mapa;
import tipos.Banco;
import externos.BancoExterno;
import externos.BufferBusquedas;
import externos.OrigenDatos;



public class testJackson {
	
		private JsonFactory jsonFactory = new JsonFactory();
		
		private Mapa mapa;
		private BufferBusquedas buffer;
		private List<BancoExterno> listaBancosExt;
		private String jsonBanco =  "[{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\", "
				+ "\"servicios\":[\"cobro cheques\", \"depósitos\", \"extracciones\", \"transferencias\", \"créditos\", \"\", \"\", \"\" ]"
				+ "}]";
		OrigenDatos banco;
		BancoExterno bancoExt;
		Direccion dire;
			
		@Before
		public void setUp() {
			bancoExt= new BancoExterno();
			dire = new Direccion();
			dire.setNumero(150);
			dire.setCalle("Siempre Viva");
			bancoExt.setDireccion(dire);
			bancoExt.setGerente("Mr. Burns");
			bancoExt.setNombre("Banco de Madera");
			mapa= new Mapa();
			buffer = new BufferBusquedas();
			banco=Mockito.mock(OrigenDatos.class);
			Mockito.when(banco.buscar("Banco de la Plaza", "cobro cheques")).thenReturn(jsonBanco);
			listaBancosExt = jsonFactory.fromJson(jsonBanco);
			mapa.setBuffer(buffer);
			mapa.agregarExterno(banco);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			Banco unBancoDesdeJson=(Banco) buffer.adaptarJsonBanco(banco.buscar("Banco de la Plaza","cobro cheques")).get(0);
			assertEquals(unBancoDesdeJson.getNombre(), "Banco de la Plaza");
			assertEquals(listaBancosExt.get(0).getGerente(),"Javier Loeschbor");
			assertEquals(listaBancosExt.get(0).getSucursal(),"Avellaneda");
			assertEquals(listaBancosExt.get(0).getServicios().get(0),"cobro cheques");
			assertEquals(listaBancosExt.get(0).getServicios().get(5),"");
		}
		@Test
		public void bancoATravesDelMapa() {
			mapa.buscar("Banco de la Plaza", "cobro cheques");
			assertEquals(bancoExt.getNombre(), "Banco de Madera");
			Mockito.verify(banco,Mockito.times(1)).buscar("Banco de la Plaza", "cobro cheques");
			assertEquals(mapa.getListaPOIS().size(),1);				//Estaba vacio y agrega el banco
		}
}
		
		
			

