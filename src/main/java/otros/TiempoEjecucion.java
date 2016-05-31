package otros;

public class TiempoEjecucion {
	private static  double time_start,time_end, tiempoEjecucion;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void Start(){
		time_start = System.currentTimeMillis();
	}
	public static void Stop(){
		time_end = System.currentTimeMillis();
	}
	public static double getTiempoEjecucion(){
		tiempoEjecucion= time_end - time_start;
		return tiempoEjecucion;
	}
}
