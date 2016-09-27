package procesos;

import java.util.ArrayList;
import java.util.List;

import configuracionTerminales.Administrador;
import configuracionTerminales.EnviadorMails;


public class ManejoDeResultadosProcesos {
//	TODO: Para qué guarda los resultados si no los usa, si solo precisa saber un resultado? - Aldana
	
//	TODO: Esto más que manejo de resultados podría llamarse ManejoDeErrores, ya que eso es lo que hace, manejar los errores.
//	No les diría nada si no preguntara si el resultado falló, pero como lo hace, entonces evidentemente sólo maneja errores. - Aldana
	private List<ResultadoDeProceso> resultados;
	
	public ManejoDeResultadosProcesos(){
		resultados=new ArrayList<ResultadoDeProceso>();
	}
	
	
	public void tratarResultado(ResultadoDeProceso resultado,Administrador admin){
		if(!resultado.isEstadoEjecucion()){ //si el resultado fallo
//			TODO: Bueno calculo que este es el punto que les falta, ya que no todos los manejo de errores
//			van a mandar mail. - Aldana
			EnviadorMails enviadorMails= new EnviadorMails();
			enviadorMails.mailFallaProceso(resultado.getTipoProceso(),admin);
		}
	}
	
	public List<ResultadoDeProceso> getTerminales() {
		return resultados;
	}
	public void agregarResultado(ResultadoDeProceso resultado) {
		this.resultados.add(resultado);
	}


	public List<ResultadoDeProceso> getResultados() {
		return resultados;
	}

}
