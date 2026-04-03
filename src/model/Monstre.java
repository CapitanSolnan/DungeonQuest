package model;

import combat.Combatent;
import utils.MathUtils;

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
		this.penalitzacio = MathUtils.ajustarRang(0, 3, penalitzacio);
		this.valorExperiencia = vida * 2;
	}

	@Override
	public int calcularAtac() {
		return MathUtils.generarNumeroAleatori(1, this.vida);
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
