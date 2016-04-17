package principal;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

public class Comuna {
	public Comuna(){
		 this.polygonoActual = new Polygon();
	}
	Polygon polygonoActual ;
	
	public void addPunto(Coordenadas coordenada){
		Point punto=new Point(coordenada.getLatitud(),coordenada.getLongitud());
        this.polygonoActual.add(punto);
	}
	
	public boolean dentroDeLaZona(Coordenadas coordenada){
		Point punto=new Point(coordenada.getLatitud(),coordenada.getLongitud());
		return polygonoActual.isInside(punto);
	}

}
