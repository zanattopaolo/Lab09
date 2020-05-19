package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		
//		System.out.println("Sono stati inseriti "+model.getNumVertex()+" vertici e "+model.getNumEdges()+" archi.\n"+model.getEdges());
		
//		System.out.println(model.getCountryDegree());
		
//		System.out.println("\n\nLe componenti connesse sono: "+model.getNumComponentiConnesse());
		
//		System.out.println("\nLe componenti connesse agli USA sono: \n"+model.getConnectedFrom(2));
		
		System.out.println("\nLe componenti connesse all'Honduras sono: \n"+model.getConnectedFrom(91));
		
//		System.out.println("\nLe componenti connesse all'Olanda sono: \n"+model.getConnectedFrom(210));
		
//		System.out.println("\nLe componenti connesse all'Honduras sono "+model.getVicini1(model.getVertex(91)).size()+" e sono: \n"+model.getVicini1(model.getVertex(91)));
		
		Set<Country> esito =model.getVicini2(model.getVertex(91));
		System.out.println("\nLe componenti connesse all'Honduras sono "+esito.size()+" e sono: \n"+esito);
		
		
		
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
