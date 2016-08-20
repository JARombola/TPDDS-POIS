package procesos;

import java.util.ArrayList;
import java.util.List;

public class ManejoDeResultadosProcesos {
	
	private List<ResultadoDeProceso> resultados;
	EnvioMail enviadorMails;
	
	public ManejoDeResultadosProcesos(){
		resultados=new ArrayList<ResultadoDeProceso>();
	}
	
	
	public void tratarResultado(ResultadoDeProceso resultado){
		if(!resultado.isEstadoEjecucion()){ //si el resultado fallo
			//if mando mail? TODO
			//enviadorMails.MandarMailDeFallaProceso(resultado.getTipoProceso());
		}
	}
	
	public List<ResultadoDeProceso> getTerminales() {
		return resultados;
	}
	public void agregarResultado(ResultadoDeProceso resultado) {
		this.resultados.add(resultado);
		this.tratarResultado(resultado);
	}


	public List<ResultadoDeProceso> getResultados() {
		return resultados;
	}

}
