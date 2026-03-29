package model;

import combat.Combatent;
import utils.MathUtils;

// BUG: Un personatge no pot fer res si no esta viu.

public class Personatge implements Combatent {
	private String nom;
	private int vida;
	private int atac;
	private int experiencia;
	private int agilitat;
	private int forsa;
	private int[] posicio = new int[2];
	private Tresor[] equipament;

	public Personatge(String nom, int vida, int atac, int agilitat, int forsa, int posicioInicial[]) {
		this.nom = (nom == null || nom.isEmpty()) ? "Steve" : nom;

		this.vida = MathUtils.ajustarRang(5, 20, vida);
		this.atac = MathUtils.ajustarRang(1, 4, atac);
		this.agilitat = MathUtils.ajustarRang(4, 11, agilitat);
		this.forsa = MathUtils.ajustarRang(4, 11, forsa);
		this.experiencia = 0;

		// Verificar que l'array tingui la mida correcta
		if (posicioInicial != null && posicioInicial.length >= 2) {
			this.posicio[0] = posicioInicial[0];
			this.posicio[1] = posicioInicial[1];
		} else {
			this.posicio[0] = 0;
			this.posicio[1] = 0;
		}

		// La quantitat d'equipament depen de la força. Inicialment buit.
		this.equipament = new Tresor[this.forsa];
	}

	public void atacar(Monstre m) {
		// TODO: Implementar atac.
	}

	public void explorar() {
		// TODO: Implementar exploració.
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

	@Override
	public int calcularAtac() {
		return MathUtils.generarNumeroAleatori(1, this.atac);
	}

	@Override
	public void rebreDany(int quantitat) {
		// TODO: Implementar rebre dany
		throw new UnsupportedOperationException("Unimplemented method 'rebreDany'");
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " | Vida: " + vida + " | Agilitat: " + agilitat + " | Força: " + forsa + " | Posició: "
				+ posicio[0] + " " + posicio[1] + " | Tresors: " + equipament;
	}

	public int getVida() {
		return vida;
	}

}
