package procesos;

public class EstadoEjecucion {
	String estado;
	
	public static EstadoEjecucion falla(Proceso proceso){
		EstadoEjecucion ejecucion = new EstadoEjecucion();
		ejecucion.estado = "Fallo";
		return ejecucion;
		
	}
	
	public static EstadoEjecucion exitoso(Proceso proceso){
		EstadoEjecucion ejecucion = new EstadoEjecucion();
		ejecucion.estado = "Exito";
		return ejecucion;
		
	}
}
