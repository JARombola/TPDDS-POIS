package principal;

public class Horario {
	private int dia;			//Domingo=1, Lunes=2.....Sabado=7
	private String inicio;		//Hora que abré
	private String fin;			//Hora que cierra
	
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFin() {
		return fin;
	}
	public void setFin(String fin) {
		this.fin = fin;
	}
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public void mostrar(){
		System.out.println(getInicio()+" "+getFin());
	}
	public boolean estaAbierto(String hora){
		int resultado=hora.compareTo(inicio);		// cuanto hace que esta abierto (si es negativo es xq esta cerrado :P)
		if (resultado>=0) {								//la cuenta es: momento-min
			resultado=hora.compareTo(fin);		// cuanto hace que cerró (si es negativo es xq todavia está abierto :P)
			if (resultado<=0){							//momento-max
				return true;
			}
		}
		return false;
	}
}
