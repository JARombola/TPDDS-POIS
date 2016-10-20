package pois;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalTime;

@Entity
public class Horario {
	
	@Id @GeneratedValue
	private int id;

	private int dia; 				// Domingo=1, Lunes=2.....Sabado=7
	private LocalTime inicio; 		// Hora que abre
	private LocalTime fin; 			// Hora que cierra

	public Horario(){
		
	}
	
	public boolean estaAbierto(LocalTime hora) {
		return (hora.isAfter(this.getInicio()) && hora.isBefore(this.getFin()));
	}

	
	//---------------------GETTERS/SETTERS------------------------------------
	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFin() {
		return fin;
	}

	public void setFin(LocalTime fin) {
		this.fin = fin;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
