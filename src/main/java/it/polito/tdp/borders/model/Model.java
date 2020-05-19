package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	private Map<Country, Country> visita;
	
	public Model() {
		
		dao=new BordersDAO();
		this.idMap=new HashMap<>();
		dao.loadAllCountries(idMap);
	}
	
	
	public void creaGrafo(int anno) {
		
		this.grafo=new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		for(Border b: dao.getCountryPairs(anno)) {
			Country c1=this.idMap.get(b.getCod1());
			Country c2=this.idMap.get(b.getCod2());
			
			if(!this.grafo.containsVertex(c1))
				this.grafo.addVertex(c1);
			
			if(!this.grafo.containsVertex(c2))
				this.grafo.addVertex(c2);
			
			if(!this.grafo.containsEdge(c1, c2) && !this.grafo.containsEdge(c2, c1))
				this.grafo.addEdge(c1, c2);
		}
		
	}
	
	public int getNumVertex() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumEdges() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Country> getVertices(){
		return this.grafo.vertexSet();
	}
	
	public String getEdges() {
		String s="";
		for(DefaultEdge df: this.grafo.edgeSet()) {
			s+=this.grafo.getEdgeSource(df).getNome()+" - "+this.grafo.getEdgeTarget(df).getNome()+"\n";
		}
		
		return s;
	}
	
	public Country getVertex(int cod) {
		return this.idMap.get(cod);
	}
	
	public String getCountryDegree(){
		String s="";
		for(Country c:this.grafo.vertexSet()) {
			s+=c.getNome()+" ha grado "+Graphs.neighborListOf(this.grafo, c).size()+"\n";
			
		}
		return s;
		
	}
	
	public int getNumComponentiConnesse() {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo);
		List<Set<Country>> connected = ci.connectedSets();
		int k=0;
		
		for(Set<Country> s: connected) {
			if(s.size()>1) {
				k+=s.size();
			}
		}
		
		return k;
		
	}
	
	public String getConnectedFrom(int cod) {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo);
		Set<Country> connected = ci.connectedSetOf(this.idMap.get(cod));
		String s=connected.size()+"\n";
		for(Country c: connected)
			s+=c.getNome()+"\n";
		
		return s ;
	}
	
	/**
	 * Metodo che implementa il BreadthFirstIterator per la ricerca dei vicini
	 * @param c stato di cui trorvare i vicini 
	 * @return lista degli stati vicini
	 */
	public Collection<Country> getVicini1(Country c){
		visita=new HashMap<>();
		List<Country> res=new ArrayList<Country>();
		visita.put(c, null);
		
		GraphIterator<Country, DefaultEdge> bfi = new BreadthFirstIterator<>(this.grafo, c);
		bfi.addTraversalListener(new TraversalListener<Country, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				Country s=grafo.getEdgeSource(e.getEdge());
				Country d=grafo.getEdgeTarget(e.getEdge());
				
				if(visita.containsKey(s) && !visita.containsKey(d))
					visita.put(d, s);
				else {
					if(visita.containsKey(d) && !visita.containsKey(s))
						visita.put(s, d);
				}
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {}
			
		});
		
		while(bfi.hasNext())
			bfi.next();
	
//		System.out.println(visita.size()+"\n"+visita);
		
		for(Country c2: visita.values())
			if(c2!= null)
				res.add(c2);
		
		return res;
		
	}
	
	
	/**
	 * Metodo di ricerca dei vicini utilizzando il procedimento ricorsivo
	 * @param c Country di cui si vogliono cercare i vicini
	 * @return Set di tutti i Country connessi
	 */
	public Set<Country> getVicini2(Country c){
		Set<Country> res=new HashSet<Country>();
		visita=new HashMap<>();
		
		this.ricercaRicorsiva(c, res);
		
		return res;
	}
	
	private void ricercaRicorsiva(Country c, Set<Country> res) {
		
		List<Country> vicini=Graphs.neighborListOf(grafo, c);
		
		//condizione di terminazione
		if(res.containsAll(vicini))
			return;
		
		for(Country cNew: vicini) {
		//Filtro sull'esplorazione
		if(res.contains(cNew))
			continue;
		
		//aggiunta vertice
		res.add(cNew);
			
		//chiamata ricorsiva 
		this.ricercaRicorsiva(cNew, res);
			
		}

	}
	

}
