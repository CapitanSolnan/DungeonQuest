package model;

import combat.Combatent;

// BUG: El monstre no pot fer res si esta mort

public class Monstre implements Combatent {

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

	public int comprovarRango(int min, int max, int valor) {
		if (valor < min || valor > max) {
			return '0';
		}
		return valor;
	}

	@Override
	public int calcularAtac() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcularAtac'");
	}

	@Override
	public void rebreDany(int quantitat) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'rebreDany'");
	}

	@Override
	public boolean estaViu() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'estaViu'");
	}

	@Override
	public int getVida() {
		return vida;
	}

	@Override
	public void setVida(int vida) {
		this.vida = (vida > 0) ? vida : 0;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " | Vida: " + vida;
	}

}
