package procesos;

import java.util.ArrayList;
import java.util.List;



public class ResultadosProcesos {		// Lo unico que hace esta clase es guardar resultados... no se si es necesaria esta clase

	//	TODO: Para qué guarda los resultados si no los usa, si solo precisa saber un resultado? - Aldana
	
	//	TODO: Esto más que manejo de resultados podría llamarse ManejoDeErrores, ya que eso es lo que hace, manejar los errores.
	//	No les diría nada si no preguntara si el resultado falló, pero como lo hace, entonces evidentemente sólo maneja errores. - Aldana
	
	private static ResultadosProcesos instancia;
	private List<DatosProceso> resultados;
	
	public ResultadosProcesos(){
		resultados=new ArrayList<DatosProceso>();
	}
	
	public static ResultadosProcesos getInstacia(){
		if(instancia==null)
			instancia = new ResultadosProcesos();
		return instancia;
	}
		
	public List<DatosProceso> getTerminales() {
		return resultados;
	}
	
	public void agregarResultado(DatosProceso resultado) {
		this.resultados.add(resultado);
	}

	public List<DatosProceso> getResultados() {
		return resultados;
	}

	public static ResultadosProcesos getInstancia() {
		return instancia;
	}

	public static void setInstancia(ResultadosProcesos instancia) {
		ResultadosProcesos.instancia = instancia;
	}

}
