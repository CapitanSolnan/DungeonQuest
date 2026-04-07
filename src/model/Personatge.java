package model;

import combat.Combatent;
import sala.Sala;
import utils.MathUtils;

public class Personatge implements Combatent {
	private String nom;
	private int vida;
	private int atac;
	private int experiencia = 0;
	private int agilitat;
	private int forsa;
	private int[] posicio = { 0, 0 };
	private Tresor[] equipament;

	public Personatge(String nom, int vida, int atac, int agilitat, int forsa) {
		this.nom = (nom == null || nom.isEmpty()) ? "Steve" : nom;

		this.setVida(MathUtils.ajustarRang(5, 20, vida));
		this.setAtac(atac);
		this.setAgilitat(agilitat);
		this.setForsa(forsa);

		// La quantitat d'equipament depen de la força. Inicialment buit.
		this.equipament = new Tresor[this.forsa];
	}

	public boolean estaViu() {
		return getVida() > 0;
	}

	public int atacar(Monstre monstre) {
		if (!potLluitar(monstre))
			return 0;

		int dany = this.calcularAtac();
		monstre.rebreDany(dany);
		return dany;
	}

	public void explorar(Sala sala) {
		if (sala.estaExplorada())
			return;

		sala.setExplorada(true);

		if (sala.getTresor() == null) {
			return;
		} else {
			this.intentarAfegirTresor(sala.getTresor());
		}
	}

	// TODO: Optimitzar moviment
	public void moure(char direccio) {
		if (direccio == 'N') {
			posicio[0] += 1;

		} else if (direccio == 'S') {
			posicio[0] -= 1;

		} else if (direccio == 'E') {
			posicio[1] += 1;

		} else if (direccio == 'O') {
			posicio[1] -= 1;

		} else {
			System.out.println("Direcció erronia");
		}
	}

	public void sumarExperiencia(int quantitat) {
		if (quantitat > 0) {
			this.experiencia += quantitat;
		}
	}

	@Override
	public int calcularAtac() {
		return MathUtils.generarNumeroAleatori(1, this.atac);
	}

	public boolean ferTiradaForsa() {
		int dau = MathUtils.generarNumeroAleatori(1, 12);
		return dau <= this.forsa;
	}

	public boolean ferTiradaAgilitat() {
		int dau = MathUtils.generarNumeroAleatori(1, 12);
		return dau <= this.agilitat;
	}

	@Override
	public int getVida() {
		return vida;
	}

	/**
	 * Cambia la vida del personatge. <br>
	 * Si el valor es menor que 0, la vida s'estableix en 0.
	 * 
	 * @param vida El nou valor de la vida.
	 */
	@Override
	public void setVida(int vida) {
		this.vida = MathUtils.ajustarRang(0, 20, vida);
	}

	public String getNom() {
		return nom;
	}

	public int getAgilitat() {
		return agilitat;
	}

	public void setAgilitat(int agilitat) {
		this.agilitat = MathUtils.ajustarRang(4, 11, agilitat);
	}

	public int getAtac() {
		return atac;
	}

	public void setAtac(int atac) {
		this.atac = MathUtils.ajustarRang(1, 4, atac);
	}

	public int getForsa() {
		return forsa;
	}

	public void setForsa(int forsa) {
		this.forsa = MathUtils.ajustarRang(4, 11, forsa);
	}

	public int[] getPosicio() {
		return posicio;
	}

	public int getPosicioX() {
		return posicio[0];
	}

	public int getPosicioY() {
		return posicio[1];
	}

	public boolean intentarAfegirTresor(Tresor tresor) {
		for (int i = 0; i < equipament.length; i++) {
			if (equipament[i] == null) {
				equipament[i] = tresor;
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " |Vida: " + vida + " | Agilitat: " + agilitat + " | Força: " + forsa + "Experiencia: "
				+ experiencia + " | Posició: " + posicio[0] + " " + posicio[1] + " | Tresors: " + equipament;
	}
}
