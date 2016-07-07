package procesos;

public class ReintentoProceso implements ManejoDeResultadosProcesos{

	int cantReintentos;

	@Override
	public void manejarError(Proceso proceso) {
		
		for(int i = 0; i <= cantReintentos; i = i + 1) {
			try{
				proceso.ejecutarProceso();
				break;
			}
			catch(Exception e) {
				//no hace nada y el proceso queda con estado de falla.  El enunciado dice que además podria querer mandar un mail. Falta eso
			}
		}
	}

}
