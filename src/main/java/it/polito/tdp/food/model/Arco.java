package it.polito.tdp.food.model;

public class Arco implements Comparable<Arco>{

	private Food food1;
	private Food food2;
	private double peso;
	public Arco(Food food1, Food food2, double peso) {
		super();
		this.food1 = food1;
		this.food2 = food2;
		this.peso = peso;
	}
	public Food getFood1() {
		return food1;
	}
	public void setFood1(Food food1) {
		this.food1 = food1;
	}
	public Food getFood2() {
		return food2;
	}
	public void setFood2(Food food2) {
		this.food2 = food2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Arco [food1=" + food1 + ", food2=" + food2 + ", peso=" + peso + "]";
	}
	@Override
	public int compareTo(Arco o) {
		
		return  (int) -(this.peso- o.getPeso());
	}
	
	
}
