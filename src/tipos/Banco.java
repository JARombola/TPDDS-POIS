package tipos;

import principal.Horario;
import principal.POI;

public class Banco extends POI{
	public Banco(){
	}
	public void setHorarios(){
		for (int dia=2;dia<=6;dia++){
			Horario horarioNuevo=new Horario();
			horarioNuevo.setDia(dia);
			horarioNuevo.setInicio("10:00");
			horarioNuevo.setFin("15:00");
			super.agregarHorario(horarioNuevo);}
	}
		/*Horario horarioNuevo=new Horario();
		DateFormat formatoHora = new SimpleDateFormat("HH:mm");
		Date fecha=new Date();
		String hora = formatoHora.format(fecha);		//Domingo=1, Lunes=2....
		Calendar c=new GregorianCalendar();
		horarioNuevo.setDia(c.get(Calendar.DAY_OF_WEEK)); //HOY
		Date inicio;
		inicio="14:32";
		Date fin;
		horarioNuevo.setInicio(inicio);
		horarioNuevo.setFin(fin);*/
		
	}

