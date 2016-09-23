package procesos;

import java.util.ArrayList;
import java.util.List;

import configuracionTerminales.Administrador;
import configuracionTerminales.EnviadorMails;


public class ManejoDeResultadosProcesos {
	private List<ResultadoDeProceso> resultados;
	
	public ManejoDeResultadosProcesos(){
		resultados=new ArrayList<ResultadoDeProceso>();
	}
	
	
	public void tratarResultado(ResultadoDeProceso resultado,Administrador admin){
		if(!resultado.isEstadoEjecucion()){ //si el resultado fallo
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
