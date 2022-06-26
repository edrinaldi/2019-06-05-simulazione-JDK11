package it.polito.tdp.crimes.model;

public class DistrettoVicino implements Comparable<DistrettoVicino>{
	private Distretto d;
	private Double peso;
	
	@Override
	public String toString() {
		return d.getNome() + " " + peso;
	}

	public DistrettoVicino(Distretto d, Double peso) {
		super();
		this.d = d;
		this.peso = peso;
	}

	@Override
	public int compareTo(DistrettoVicino o) {
		// TODO Auto-generated method stub
		return this.peso.compareTo(o.peso);
	}
	
	
}
