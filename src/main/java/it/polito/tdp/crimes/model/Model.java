package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	private EventsDao dao;
	private Simulator sim;
	
	public Model() {
		// inizializzo il dao
		this.dao = new EventsDao();
		
	}
	
	public void creaGrafo(int anno) {
		// inizializzo il grafo
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(anno));
		
		// aggiungo gli archi
		for(Distretto d1 : this.grafo.vertexSet()) {
			for(Distretto d2 : this.grafo.vertexSet()) {
				if(!d1.equals(d2) && this.grafo.getEdge(d2, d1) == null) {
					double peso = LatLngTool.distance(d1.getCentro(), d2.getCentro(), LengthUnit.KILOMETER);
					Graphs.addEdge(this.grafo, d1, d2, peso);
				}
			}
		}
		
		// console
		System.out.printf("# vertici: %d\n", this.grafo.vertexSet().size());
		System.out.printf("# archi: %d\n", this.grafo.edgeSet().size());
	}
	
	public List<DistrettoVicino> getAdiacenti(Distretto d) {
		List<DistrettoVicino> adiacenti = new ArrayList<>();
		for(Distretto v : Graphs.neighborListOf(this.grafo, d)) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(d, v));
			adiacenti.add(new DistrettoVicino(v, peso));
		}
		Collections.sort(adiacenti);
		return adiacenti;

	}
	
	public int simula(int anno, int mese, int giorno, int N) {
		this.sim = new Simulator(this.grafo);
		this.sim.init(anno, mese, giorno, N);
		this.sim.run();
		return this.sim.getNMalGestiti();
	}
	
	public List<Distretto> getVertici() {
		return new ArrayList<Distretto>(this.grafo.vertexSet());
	}
	
	public boolean isGrafoCreato() {
		return this.grafo!=null;
	}
}
