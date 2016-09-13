package tiposPoi;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;

import pois.Comuna;
import pois.ListaServicios;
import pois.POI;
import terminales.Maquina;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class CGP extends POI {
	private Comuna comuna;
	@OneToOne @Cascade(value = CascadeType.ALL)
	private ListaServicios servicios;

	public CGP() {
		servicios = new ListaServicios();
	}

	public void modificar(Banco poiEntrante) {
		if (poiEntrante.getServicios().getServicios().size() > 0) {
			servicios = poiEntrante.getServicios();
		}
		super.modificar(poiEntrante);
	}

	// ------------------------DISPONIBILIDAD------------------

	public void agregarServicio(Servicio unServicio) {
		this.servicios.agregarServicio(unServicio);
	}

	public boolean estaDisponible(int dia, LocalTime hora, String servicioBuscado) {
		return getServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	// ---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (puntoActual.getComuna().dentroDeLaZona(puntoActual.getCoordenadas()));

	}

	// ---------------BUSQUEDA-----------------------------------

	public boolean tienePalabra(String texto) {
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));

	}

	public boolean tienePalabraEnServicio(String texto) {
		return getServicios().tienePalabra(texto);
	}

	// -------------------GETTERS,SETTERS-----------------
	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public ListaServicios getServicios() {
		return servicios;
	}

	public void setServicios(ListaServicios servicios) {
		this.servicios = servicios;
	}

}
