package tipos;

import principal.Maquina;

public class CGP {
	private int comuna;

	public CGP() {

	}

	public boolean estaCerca(Maquina puntoActual) {
		return (comuna == puntoActual.getComuna());
	}

	// -------------------GETTERS,SETTERS-----------------
	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;

	}
	//-------------------HORARIOS-----------------------
	
}
