package configuracionTerminales;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class AdapterBooleano {
	
	@Type(type="yes_no")
	private boolean activado;

	public AdapterBooleano(){
		
	};
	
	public AdapterBooleano(boolean activado){
		this.activado=activado;
	}
	
	public boolean isActivado() {
		return activado;
	}

	public void setValor(boolean valor) {
		this.activado = valor;
	}
	

}
