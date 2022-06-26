package it.polito.tdp.crimes.model;

import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	// dati in ingresso
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	private int anno;
	private int mese;
	private int giorno;
	private int N;
	
	// dati in uscita
	private int nMalGestiti;
	
	// modello del mondo
	
	
	// coda degli eventi
	Queue<SimulationEvent> queue;
	
	public Simulator(Graph<Distretto, DefaultWeightedEdge> grafo) {
		// inizializzo il grafo
		this.grafo = grafo;
	}
	
	public void init(int anno, int mese, int giorno, int N) {
		// TODO
	}
	
	public void run() {
		// finche' la coda non è vuota
		while(!this.queue.isEmpty()) { 
			
			// estraggo il singolo evento
			SimulationEvent e = this.queue.poll();
			
			// lo processo
			this.processEvent(e);
		}
	}
	
	public void processEvent(SimulationEvent e) {
		switch(e.getType()) {
		// TODO
		}
	}
	
	public int getNMalGestiti() {
		return this.nMalGestiti;
	}
}
