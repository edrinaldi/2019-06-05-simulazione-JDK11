package it.polito.tdp.crimes.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	private int nome;
	private LatLng centro;
	public Distretto(int nome, double lat, double lng) {
		super();
		this.nome = nome;
		this.centro = new LatLng(lat, lng);
	}
	public int getNome() {
		return nome;
	}
	public void setNome(int nome) {
		this.nome = nome;
	}

	public LatLng getCentro() {
		return centro;
	}
	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distretto other = (Distretto) obj;
		return Objects.equals(nome, other.nome);
	}
	@Override
	public String toString() {
		return "Distretto [nome=" + nome + ", lat=" + centro.getLatitude() + ", lng=" + centro.getLongitude() + "]";
	}
	
	
}
