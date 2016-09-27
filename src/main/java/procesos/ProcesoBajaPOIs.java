package procesos;

import java.util.List;
import java.util.stream.Collectors;

import terminales.ControlTerminales;
import terminales.Terminal;

public class ProcesoBajaPOIs extends Proceso{
	private int IDPoiEliminar;

	public ProcesoBajaPOIs(int id, ControlProcesos control, ControlTerminales terminales) {
		super(control,terminales);
		this.setIDPoiEliminar(id);
	}
	
	public int ejecutarProceso() {
		List<Terminal> terminalesAfectadas=getCentralTerminales().getTerminales()
				.stream()
				.filter(terminal->terminal.getMapa().getPOI(IDPoiEliminar)!=null)
				.collect(Collectors.toList());

		getCentralTerminales().getTerminales()
		.stream().forEach(terminal->{
			try {
//				TODO: Es un smell pedirle a un objeto algo y mandarle mensajes a ese algo
//				(message chains). Podrían hacer que el mapa sea un singleton (busquen el patrón)
//				para que no tengan que asignarle la instancia a la terminal, sino que desde 
//				cualquier punto del sistema puedan acceder a la misma instancia. - Aldana
				terminal.getMapa().eliminarPOI(IDPoiEliminar);
			} catch (Exception e) {				//la atrapa el controlProcesos
//				TODO: El código de este try/catch está repetido. Estaría bueno que esto se hiciera en un solo lugar.
//				Es más, si bien atrapar una excepción genérica es un smell, y frecuentemente un mal manejo de excepciones
//				En este caso no lo es, por lo que no haría falta wrappear la excepción en un tipo de excepción más específica. - Aldana
				throw new ExcepcionFallo(terminal);
			}
		});
//		TODO: Esto no tiene sentido porque todas las terminales deberían conocer los mismos pois, por lo tanto, el
//		número que esto debería devolver es la cantidad de pois eliminados. 
//		Por otro lado, no está bueno que usen una variable que no tiene nada que ver para devolver este resultado
//		Hay ahí un acoplamiento implícito grande. - Aldana
		return terminalesAfectadas.size();
	}

	public int getIDPoiEliminar() {
		return IDPoiEliminar;
	}

	public void setIDPoiEliminar(int iDPoiEliminar) {
		IDPoiEliminar = iDPoiEliminar;
	}
	
}

