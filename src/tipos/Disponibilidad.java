package tipos;

public interface Disponibilidad {
	public boolean estaDisponible(int dia, String hora, String palabra);
	public boolean estaDisponible(int dia, String hora);
}
