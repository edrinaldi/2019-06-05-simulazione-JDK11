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
	private Event crimine;
	
	
	public SimulationEvent(LocalDateTime time, EventType type, Agente agente, Event crimine) {
		super();
		this.time = time;
		this.type = type;
		this.agente = agente;
		this.crimine = crimine;
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




	public Event getCrimine() {
		return crimine;
	}


	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}


	@Override
	public int compareTo(SimulationEvent o) {
		return this.time.compareTo(o.time);
	}

}
