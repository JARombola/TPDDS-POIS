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

@org.mongodb.morphia.annotations.Entity(value="POI")
@Entity
public class CGP extends POI {
	@org.mongodb.morphia.annotations.Embedded
	@OneToOne @Cascade(value=CascadeType.ALL)
	private Comuna comuna;
	
	@org.mongodb.morphia.annotations.Embedded
	@OneToOne @Cascade(value = CascadeType.ALL)
	private ListaServicios listaServicios;

	public CGP() {
		super();
		listaServicios = new ListaServicios();
	}

	public void modificar(CGP poiEntrante) {
		if (poiEntrante.getListaServicios().getServicios().size() > 0) {
			listaServicios = poiEntrante.getListaServicios();
		}
		super.modificar(poiEntrante);
	}

	// ------------------------DISPONIBILIDAD------------------

	public void agregarServicio(Servicio unServicio) {
		this.listaServicios.agregarServicio(unServicio);
	}

	public boolean estaDisponible(int dia, LocalTime hora, String servicioBuscado) {
		return getListaServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	// ---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (puntoActual.getComuna().dentroDeLaZona(puntoActual.getCoordenadas()));

	}

	// ---------------BUSQUEDA-----------------------------------

	public boolean tienePalabra(String texto) {
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));

	}

	private boolean tienePalabraEnServicio(String texto) {
		return getListaServicios().tienePalabra(texto);
	}

	// -------------------GETTERS,SETTERS-----------------
	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public ListaServicios getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(ListaServicios servicios) {
		this.listaServicios = servicios;
	}

}
