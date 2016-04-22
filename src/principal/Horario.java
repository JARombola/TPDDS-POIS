package principal;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Horario {
	private int dia; // Domingo=1, Lunes=2.....Sabado=7
	private LocalTime inicio; // Hora que abré
	private LocalTime fin; // Hora que cierra

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

	public void mostrar() {
		System.out.println(getInicio() + " " + getFin());
	}

	public boolean estaAbierto(LocalTime hora) {
		return (hora.isAfter(this.getInicio()) && hora.isBefore(this.getFin()));
	}
}
