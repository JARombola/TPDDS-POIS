package procesos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra extends Proceso {
	
	public ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra() {
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(2);
			DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date2 = new Date();
			System.out.println(dateFormat2.format(date2));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
