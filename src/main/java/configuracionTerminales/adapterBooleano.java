package configuracionTerminales;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class adapterBooleano {
	
	@Type(type="yes_no")
	private boolean activado;

	public adapterBooleano(){
		
	};
	
	public adapterBooleano(boolean activado){
		this.activado=activado;
	}
	
	public boolean isActivado() {
		return activado;
	}

	public void setValor(boolean valor) {
		this.activado = valor;
	}
	

}
