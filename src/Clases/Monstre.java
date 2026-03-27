package Clases;

public class Monstre {

	private String nom;
	private int vida;
	private int penalitzacio;
	private int valorExperiencia;
	
	public Monstre(String nom, int vida, int penalitzacio) {
		super();
		this.nom = nom;
		this.vida = vida;
		this.penalitzacio = comprovarRango(0, 3, penalitzacio);
		this.valorExperiencia = vida * 2;
	}
	
	public int comprovarRango(int min, int max, int valor){
		if (valor < min || valor > max){
			return '0';
		}
		return valor;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nom: " + nom + " | Vida: " + vida;
	}
	
	
}
