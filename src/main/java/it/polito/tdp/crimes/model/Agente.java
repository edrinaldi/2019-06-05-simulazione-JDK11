package it.polito.tdp.crimes.model;

public class Agente {
	private int id;
	private Distretto distretto;
	private boolean disponibile;
	public Agente(int id, Distretto distretto, boolean disponibile) {
		super();
		this.id = id;
		this.distretto = distretto;
		this.disponibile = disponibile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Distretto getDistretto() {
		return distretto;
	}
	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	
	
}
