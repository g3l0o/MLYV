package mlyv.app;

public class Lugar {
	String id;
	String nombre;
	String lat;
	String lon;
	String tipo;
	
	public Lugar(String id, String nombre, String lat, String lon, String tipo){
		this.id = id;
		this.nombre = nombre;
		this.lat = lat;
		this.lon = lon;
		this.tipo = tipo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
