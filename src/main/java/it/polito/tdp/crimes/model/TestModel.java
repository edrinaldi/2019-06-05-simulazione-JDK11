package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		
		m.creaGrafo(2015);
		Distretto dTest = m.getVertici().get(0);
//		System.out.println(dTest);
//		System.out.println(m.getAdiacenti(dTest));
		System.out.println(m.simula(2015, 1, 1, 10));

	}

}
