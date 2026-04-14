package model;

import combat.Combatent;
import sala.Sala;
import utils.ConsoleUtils;
import utils.MathUtils;

public class Personatge implements Combatent {
	private String nom;

	// atributs del personatge
	private int vida;
	private int atac;
	private int agilitat;
	private int forsa;

	private int experiencia = 0;
	private int[] posicio = { 0, 0 };
	private Tresor[] equipament;
	private int puntsDisponibles = 0;
	private int puntsInvertits = 0;
	public static final int PUNTS_LIMITS = 32;

	// punts per al jugador segons la dificultat
	public static final int PUNTS_DIFICULTAT_FACIL = 32;
	public static final int PUNTS_DIFICULTAT_NORMAL = 12;
	public static final int PUNTS_DIFICULTAT_DIFICIL = 0;

	public Personatge(String nom, int dificultat) {
		this.nom = (nom == null || nom.isEmpty()) ? "Steve" : nom;

		// iniciar valors per defecte amb el minim d'atributs
		// com que els setters tenen validacions amb un minim
		this.setVida(Atributs.VIDA.getMinimInicial());
		this.setAtac(Atributs.ATAC.getMinimInicial());
		this.setAgilitat(Atributs.AGILITAT.getMinimInicial());
		this.setForsa(Atributs.FORSA.getMinimInicial());

		// iniciar punts disponibles segons la dificultat
		this.setPuntsDisponiblesSegonsDificultat(dificultat);

		// TODO: cambiar a un array extensible o iniciar amb la mida del maxim de força
		// posible i fer validacions en intentarAfegirTresor

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

	// TODO: corregir moviment
	public void moure(char direccio, int midaMasmorra) {
		if (direccio == 'W') {
			if (posicio[0] - 1 < 0) {
				System.out.println("⚠ Direcció invàlida!");
				ConsoleUtils.dormirSegons(1.5);
				return;
			}
			posicio[0]--;
		} else if (direccio == 'S') {
			if (posicio[0] + 1 >= midaMasmorra) {
				System.out.println("⚠ Direcció invàlida!");
				ConsoleUtils.dormirSegons(1.5);
				return;
			}
			posicio[0]++;
		} else if (direccio == 'D') {
			if (posicio[1] + 1 >= midaMasmorra) {
				System.out.println("⚠ Direcció invàlida!");
				ConsoleUtils.dormirSegons(1.5);
				return;
			}
			posicio[1]++;
		} else if (direccio == 'A') {
			if (posicio[1] - 1 < 0) {
				System.out.println("⚠ Direcció invàlida!");
				ConsoleUtils.dormirSegons(1.5);
				return;
			}
			posicio[1]--;
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

	public boolean intentarAfegirTresor(Tresor tresor) {
		for (int i = 0; i < equipament.length; i++) {
			if (equipament[i] == null) {
				equipament[i] = tresor;
				return true;
			}
		}
		return false;
	}

	public boolean aplicarPunts(Atributs stat, int quantitat) {
		if (stat == null || quantitat <= 0)
			return false;

		int valorActual = getValorPerAtribut(stat);

		if (valorActual + quantitat > stat.getMaxim()) {
			return false;
		}

		switch (stat) {
			case VIDA -> setVida(vida + quantitat);
			case ATAC -> setAtac(atac + quantitat);
			case AGILITAT -> setAgilitat(agilitat + quantitat);
			case FORSA -> setForsa(forsa + quantitat);
		}

		setPuntsDisponibles(getPuntsDisponibles() - quantitat);
		return true;
	}

	private int getValorPerAtribut(Atributs stat) {
		return switch (stat) {
			case VIDA -> this.vida;
			case ATAC -> this.atac;
			case AGILITAT -> this.agilitat;
			case FORSA -> this.forsa;
		};
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
		this.vida = MathUtils.ajustarRang(0, Atributs.VIDA.getMaxim(), vida);
	}

	public String getNom() {
		return nom;
	}

	public int getAgilitat() {
		return agilitat;
	}

	public void setAgilitat(int agilitat) {
		this.agilitat = MathUtils.ajustarRang(Atributs.AGILITAT.getMinimInicial(), Atributs.AGILITAT.getMaxim(), agilitat);
	}

	public int getAtac() {
		return atac;
	}

	public void setAtac(int atac) {
		this.atac = MathUtils.ajustarRang(Atributs.ATAC.getMinimInicial(), Atributs.ATAC.getMaxim(), atac);
	}

	public int getForsa() {
		return forsa;
	}

	public void setForsa(int forsa) {
		this.forsa = MathUtils.ajustarRang(Atributs.FORSA.getMinimInicial(), Atributs.FORSA.getMaxim(), forsa);
	}

	public int[] getPosicio() {
		return posicio;
	}

	public int getPosicioX() {
		return posicio[0];
	}

	public int setPosicioY() {
		return posicio[1];
	}

	public int setPosicioX(int x) {
		return posicio[0] + x;
	}

	public int setPosicioY(int y) {
		return posicio[1] + y;
	}

	public int getPuntsDisponibles() {
		return puntsDisponibles;
	}

	public void setPuntsDisponibles(int puntsDisponibles) {
		this.puntsDisponibles = Math.max(0, puntsDisponibles);
	}

	// TODO: optimitzar utilitzant enums per evitar complicacions
	public void setPuntsDisponiblesSegonsDificultat(int dificultat) {
		switch (dificultat) {
			case 1 -> this.puntsDisponibles = PUNTS_DIFICULTAT_FACIL;
			case 2 -> this.puntsDisponibles = PUNTS_DIFICULTAT_NORMAL;
			case 3 -> this.puntsDisponibles = PUNTS_DIFICULTAT_DIFICIL;
		}
	}

	public int getPuntsInvertits() {
		return puntsInvertits;
	}

	public void setPuntsInvertits(int puntsInvertits) {
		this.puntsInvertits = puntsInvertits;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " |Vida: " + vida + " | Agilitat: " + agilitat + " | Força: " + forsa + " | Experiencia: "
				+ experiencia + " | Posició: " + posicio[0] + " " + posicio[1] + " | Tresors: " + equipament;
	}
}
