package principal;

import java.text.SimpleDateFormat;
import java.util.List;

public class Terminal {
	private List<Busqueda> almacenamientoBusquedas; //teoricamente deberia ya estar ordenado por fecha porque se van guardando a medida que se hacen, pero despues veo de ordenarlo por las dudas

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public void reporteFechas(){ //si, ya se que es una negrada esto. Ni lo mencionen(? 
		int cantBusqPorFecha = 1;
		int cantBusq = almacenamientoBusquedas.size();
		
		for (int i = 0; i < cantBusq; i++) {
			if(i==(cantBusq-1) || !((almacenamientoBusquedas.get(i).getFecha()).equals((almacenamientoBusquedas.get(i+1).getFecha())))){
				System.out.println(dateFormat.format(almacenamientoBusquedas.get(i).getFecha()));
				System.out.println(cantBusqPorFecha);
				cantBusqPorFecha = 0;
			}
			cantBusqPorFecha = cantBusqPorFecha + 1;
		}
	}
	
	public void setAlmacenamientoBusquedas(List<Busqueda> almacenamientoBusquedas) {
		this.almacenamientoBusquedas = almacenamientoBusquedas;
	}
	
}
