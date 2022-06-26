package it.polito.tdp.crimes.model;

public class SimulationEvent implements Comparable<SimulationEvent>{
	public enum EventType {
		// TODO
		INIZIA_GESTIONE,
		FINISCE_GESTIONE,
	}
	
	private Integer time;
	private EventType type;
	private int agente;
	private Distretto distretto;
	
	
	
	public Integer getTime() {
		return time;
	}



	public void setTime(Integer time) {
		this.time = time;
	}



	public EventType getType() {
		return type;
	}



	public void setType(EventType type) {
		this.type = type;
	}



	@Override
	public int compareTo(SimulationEvent o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}

}
