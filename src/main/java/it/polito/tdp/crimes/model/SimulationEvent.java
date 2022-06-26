package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class SimulationEvent implements Comparable<SimulationEvent>{
	public enum EventType {
		CRIMINE,
		INIZIA_GESTIONE,
		FINISCE_GESTIONE
	}
	
	private LocalDateTime time;
	private EventType type;
	private Agente agente;
	private Distretto distretto;
	private String categoria;
	
	
	public SimulationEvent(LocalDateTime time, EventType type, Agente agente, Distretto distretto, String categoria) {
		super();
		this.time = time;
		this.type = type;
		this.agente = agente;
		this.distretto = distretto;
		this.categoria = categoria;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public Agente getAgente() {
		return agente;
	}


	public void setAgente(Agente agente) {
		this.agente = agente;
	}


	public LocalDateTime getTime() {
		return time;
	}



	public void setTime(LocalDateTime time) {
		this.time = time;
	}



	public EventType getType() {
		return type;
	}



	public void setType(EventType type) {
		this.type = type;
	}



	public Distretto getDistretto() {
		return distretto;
	}



	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}



	@Override
	public int compareTo(SimulationEvent o) {
		return this.time.compareTo(o.time);
	}

}
