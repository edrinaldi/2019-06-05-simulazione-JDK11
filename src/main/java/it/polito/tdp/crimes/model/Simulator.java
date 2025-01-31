package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.SimulationEvent.EventType;

public class Simulator {
	// dati in ingresso
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	private EventsDao dao;
	private int anno;
	private int mese;
	private int giorno;
	private int N;
	private Distretto centrale;
	private List<Event> criminiDelGiorno;
	
	// dati in uscita
	private int nMalGestiti;
	
	// modello del mondo
	private List<Agente> agenti;
	
	// coda degli eventi
	Queue<SimulationEvent> queue;
	
	public Simulator(Graph<Distretto, DefaultWeightedEdge> grafo) {
		// inizializzo il grafo
		this.grafo = grafo;
		
		// inizializzo il dao
		this.dao = new EventsDao();
	}
	
	public void init(int anno, int mese, int giorno, int N) {
		// inizializzo i dati in ingresso
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.N = N;
		this.centrale = this.trovaCentrale(this.anno);
		this.criminiDelGiorno = new ArrayList<>(this.trovaCrimini(this.anno, this.mese, this.giorno));
		System.out.println("Crimini del giorno: " + this.criminiDelGiorno.size());

		// inizializzo i dati in uscita
		this.nMalGestiti = 0;
		
		// inizializzo il modello del mondo
		this.agenti = new ArrayList<>();
		if(centrale != null) {
			// tutti gli agenti originariamente disponibili in centrale
			for(int i=0; i<this.N; i++) {
				this.agenti.add(new Agente(i, centrale, true));
			}
		}
		else {
			System.out.println("Errore: centrale non trovata.");
		}
		
		// inizializzo la coda e la carico con i primi eventi
		this.queue = new PriorityQueue<>();
		for(Event e : this.criminiDelGiorno) {
			Distretto distretto = this.trovaDistretto(e.getDistrict_id());
			if(distretto != null) {
				this.queue.add(new SimulationEvent(e.getReported_date(), EventType.CRIMINE, null, e));
			}
			else {
				System.out.println("Errore: distretto non trovato.");
			}
		}
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
		case CRIMINE:
			System.out.println("NUOVO CRIMINE!");
			
			// trovo l'agente disponibile più vicino
			Agente agente = this.trovaAgente(this.trovaDistretto(e.getCrimine().getDistrict_id()));
			if(agente == null) {
//				System.out.println("non ci sono agenti disponibili!");
				System.out.println("CRIMINE MAL GESTITO!");
				
				// aggiorno dati in uscita
				this.nMalGestiti++;
				break;
			}
			
			// calcolo il tempo d'arrivo
			double tempoSpostamento = this.calcoloSpostamento(agente.getDistretto(), 
					this.trovaDistretto(e.getCrimine().getDistrict_id()));
			
			// creo un evento per la gestione del crimine
			this.queue.add(new SimulationEvent(e.getTime().plusMinutes((long)tempoSpostamento), 
					EventType.INIZIA_GESTIONE, agente, e.getCrimine()));
			
			break;
		case INIZIA_GESTIONE:
			System.out.println("ARRIVATO AGENTE SUL POSTO!");
			
			if(e.getTime().isAfter(e.getCrimine().getReported_date().plusMinutes(15))) {
				// aggiorno i dati in uscita
				System.out.println("CRIMINE MAL GESTITO!");
				this.nMalGestiti++;
				break;
			}
			
			// calcolo l'istante in cui l'agente si libera
			LocalDateTime istanteFine = this.calcolaFineGestione(e.getTime(), 
					e.getCrimine().getOffense_category_id());
			
			// aggiorno il modello del mondo (agente impegnato nel distretto)
			e.getAgente().setDisponibile(false);
			e.getAgente().setDistretto(this.trovaDistretto(e.getCrimine().getDistrict_id()));
			
			// creo un evento per la fine della gestione
			this.queue.add(new SimulationEvent(istanteFine, EventType.FINISCE_GESTIONE, e.getAgente(), 
					e.getCrimine()));
			
			break;
		case FINISCE_GESTIONE:
			
			// aggiorno il modello del mondo
			e.getAgente().setDisponibile(true);
			
			break;
		}
	}

	// metodi per i risultati della simulazione



	public int getNMalGestiti() {
		return this.nMalGestiti;
	}
	
	// metodi ausiliari
	
	private Distretto trovaDistretto(int district_id) {
		Distretto distretto = null;
		for(Distretto d : this.grafo.vertexSet()) {
			if(d.getNome() == district_id) {
				distretto = d;
			}
		}
		return distretto;
	}

	private List<Event> trovaCrimini(int anno, int mese, int giorno) {
		return this.dao.getEventsByDay(anno, mese, giorno);
	}

	private Distretto trovaCentrale(int anno) {
		int idCentrale = this.dao.getCentrale(anno);
		return this.trovaDistretto(idCentrale);
	}
	
	private LocalDateTime calcolaFineGestione(LocalDateTime time, String categoria) {
		LocalDateTime istanteFine = null;
		long durata = 0;
		if(categoria.compareTo("all_other_crimes") == 0) {
			if(Math.random() < 0.5) {
				durata = 60;	// min
			}
			else {
				durata = 120;	// min
			}
		}
		else {
			durata = 120; // min
		}
		istanteFine = time.plusMinutes(durata);
		return istanteFine;
	}
	
	/*
	 * trova l'agente libero più vicino al distretto in cui si è verificato l'evento criminoso
	 */
	private Agente trovaAgente(Distretto distretto) {
		Agente piuVicino = null;
		double distanzaMin = Double.MAX_VALUE;
		for(Agente a : this.agenti) {
			if(a.isDisponibile()) {
				double distanza;
				if(!a.getDistretto().equals(distretto)) {
					distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(a.getDistretto(), distretto));
				}
				else {
					distanza = 0;
				}
				if(distanza < distanzaMin) {
					distanzaMin = distanza;
					piuVicino = a;
				}
			}
		}
		return piuVicino;
	}

	private double calcoloSpostamento(Distretto partenza, Distretto arrivo) {
		double distanza;	// km
		if(!partenza.equals(arrivo)) {
			distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, arrivo));
		}
		else {
			distanza = 0;
		}
		return (distanza/60)*60;	// min
	}
}
