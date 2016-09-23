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
				terminal.getMapa().eliminarPOI(IDPoiEliminar);
			} catch (Exception e) {				//la atrapa el controlProcesos
				throw new ExcepcionFallo(terminal);
			}
		});
		return terminalesAfectadas.size();
	}

	public int getIDPoiEliminar() {
		return IDPoiEliminar;
	}

	public void setIDPoiEliminar(int iDPoiEliminar) {
		IDPoiEliminar = iDPoiEliminar;
	}
	
}

