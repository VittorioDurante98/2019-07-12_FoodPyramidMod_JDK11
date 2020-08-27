package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {

	private FoodDAO dao;
	private Graph<Food, DefaultWeightedEdge> grafo;
//	private List<Food> vertici;
	private List<Food> cibi;
	private List<Arco> archi;
	
	public Model() {
		this.dao= new FoodDAO();
		//this.vertici= new ArrayList<Food>();
	}


	public List<Food> getCibi() {
		return cibi;
	}


	public void setCibi(int porzioni) {
		this.cibi= new ArrayList<>(dao.listFoodWithPortions(porzioni));
	}

	/*public List<Food> getVertici() {
		return vertici;
	}

	public void setVertici(int porzioni) {
		this.vertici = dao.listaVertici(porzioni);
	}
	
	public void creaGrafo(int porzioni) {
		this.grafo = new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.setVertici(porzioni);
		Graphs.addAllVertices(grafo, vertici);
		
		
	}*/
	public void creaGrafo(int porzioni) {
		this.grafo= new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.cibi);
		
		for (Arco a : dao.listArchi(porzioni)) {
			if(!a.getFood1().equals(a.getFood2()) && !grafo.containsEdge(a.getFood1(), a.getFood2()) 
					&& grafo.containsEdge(a.getFood2(), a.getFood1())) {
				Graphs.addEdgeWithVertices(grafo, a.getFood1(), a.getFood2(), a.getPeso());
			}
		}
		
		this.archi= new ArrayList<>(dao.listArchi(porzioni));
	}

	public List<Arco> getArchi() {
		return archi;
	}


	public String calMax(Food food) {
		String elenco="";
		List<Arco> adiacenti= new LinkedList<>();
		
		for(Arco a: archi) {
			if(a.getFood1().equals(food) || a.getFood2().equals(food)) {
				adiacenti.add(a);
			}
		}
		
		Collections.sort(adiacenti);
		
		for (int j=0; j<5 && j<adiacenti.size(); j++) {
			if(adiacenti.get(j).getFood1().equals(food)) {
				elenco+= adiacenti.get(j).getFood2().getDisplay_name()+" "+adiacenti.get(j).getPeso()+"\n";
			}else {
				elenco+= adiacenti.get(j).getFood1().getDisplay_name()+" "+adiacenti.get(j).getPeso()+"\n";
			}
		}
		return elenco;
	}
	
	
}
