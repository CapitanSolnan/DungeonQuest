import java.util.Scanner;

import core.Masmorra;
import model.Personatge;
import model.Tresor;
import model.Atributs;
import model.Monstre;
import sala.Sala;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;

public class Main {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina(); // neteja inicial per alinear vista consola

		imprimirMissatgeBenvinguda(teclado);

		// demanar el nom del jugador
		String nom = demanarNom(teclado);

		// escollir dificultat
		int dificultat = escollirDificultat(teclado);

		// crear personatge
		Personatge personatge = new Personatge(nom, dificultat);

		repartirPunts(teclado, personatge, dificultat);

		// demanar mida masmorra
		int[] midaMasmorra = demanarMidaMasmorra(teclado);

		// crear objecte masmorra
		Masmorra masmorra = new Masmorra(midaMasmorra[0], midaMasmorra[1], personatge);

		triarQueFer(teclado, personatge, masmorra);

		// TODO: Bucle del juego
	}

	private static void imprimirMissatgeBenvinguda(Scanner teclado) {
		System.out.println(Colors.CIAN);
		System.out.println(" ____  _   _ _   _  ____ _____ ___  _   _    ___  _   _ _____ ____ _____ ");
		System.out.println("|  _ \\| | | | \\ | |/ ___| ____/ _ \\| \\ | |  / _ \\| | | | ____/ ___|_   _|");
		System.out.println("| | | | | | |  \\| | |  _|  _|| | | |  \\| | | | | | | | |  _| \\___ \\ | |  ");
		System.out.println("| |_| | |_| | |\\  | |_| | |__| |_| | |\\  | | |_| | |_| | |___ ___) || |  ");
		System.out.println("|____/ \\___/|_| \\_|\\____|_____\\___/|_| \\_|  \\__\\_\\\\___/|_____|____/ |_|  ");
		System.out.println(Colors.VERD + "Fet per: Javi i Marc");
		System.out.println("DAM 1M - Programació");
		System.out.println("CURS 2025-2026");
		System.out.println(Colors.RESET);
		System.out.println("Cliqueu \"Enter\" per començar...");
		teclado.nextLine();
	}

	private static String demanarNom(Scanner teclado) {
		ConsoleUtils.saltarPagina(
				Estils.TITOL + " === Creació de personatge === " + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "Trieu un nom per al vostre jugador: ");
		System.out.print(Estils.RESPOSTA);
		String nom = teclado.nextLine();
		return nom;
	}

	public static int escollirDificultat(Scanner teclado) {
		int dificultat = 0;
		boolean esDificultatValida = false;

		do {
			ConsoleUtils.saltarPagina(Estils.TITOL + "=== Escollir dificultat ===" + Colors.RESET);

			System.out.println("  " + Colors.VERD + "1. Fàcil");
			System.out.println("  " + Colors.GROC + "2. Normal");
			System.out.println("  " + Colors.VERMELL + "3. Difícil" + Colors.RESET);

			System.out.println(Estils.PREGUNTA + "Quina dificultat vols? (1-3): ");
			System.out.print(Estils.RESPOSTA);

			try {
				int dificultatCache = Integer.parseInt(teclado.nextLine());

				if (dificultatCache >= 1 && dificultatCache <= 3) {
					dificultat = dificultatCache;
					esDificultatValida = true;
				} else {
					System.out.println(Colors.VERMELL + "⚠ Tria un número entre 1 i 3." + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
				}
			} catch (NumberFormatException e) {
				System.out.println(Colors.VERMELL + "⚠ Has d'escollir un número vàlid!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
			}
		} while (!esDificultatValida);

		return dificultat;
	}

	public static int[] demanarMidaMasmorra(Scanner teclado) {
		int[] mides = new int[2];
		String[] eixos = { "X (Horitzontal)", "Y (Vertical)" };

		for (int i = 0; i < eixos.length; i++) {
			boolean valid = false;
			while (!valid) {
				ConsoleUtils.saltarPagina(Estils.TITOL + "=== Mida de la masmorra ===" + Colors.RESET);

				// mostrar X si estem preguntant Y
				if (i == 1) {
					System.out.println("X seleccionada: " + Colors.VERD + mides[0] + Colors.RESET + "\n");
				}

				try {
					System.out.println(Estils.PREGUNTA + "Introdueix " + eixos[i] + ": ");
					System.out.print(Estils.RESPOSTA);
					int valor = Integer.parseInt(teclado.nextLine());

					if (valor >= Masmorra.MIN_MIDA_MASMORRA && valor <= Masmorra.MAX_MIDA_MASMORRA) {
						mides[i] = valor;
						valid = true;
					} else {
						System.out.println(
								Colors.VERMELL + "⚠ La mida ha de ser entre " + Masmorra.MIN_MIDA_MASMORRA + " i "
										+ Masmorra.MAX_MIDA_MASMORRA + "." + Colors.RESET);
						ConsoleUtils.dormirSegons(1.5);
					}
				} catch (NumberFormatException e) {
					System.out.println(Colors.VERMELL + "⚠ Introdueix un número sencer vàlid." + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
				}
			}
		}
		return mides;
	}

	public static void repartirPunts(Scanner teclado, Personatge personatge, int dificultat) {
		boolean finalitzar = false;
		if (dificultat == 3) {
			mostrarAtributs(personatge);
			System.out.println(Estils.NEGRETA + Colors.VERMELL
					+ "Has escollit la dificultat difícil, el teu personatge començarà amb els punts al mínim."
					+ Colors.RESET);
			ConsoleUtils.dormirSegons(3);
			finalitzar = true;

		}
		if (dificultat == 1) {
			personatge.setVida(20);
			personatge.setAtac(4);
			personatge.setAgilitat(11);
			personatge.setForsa(11);

			mostrarAtributs(personatge);
			System.out.println(Estils.NEGRETA + Colors.VERMELL
					+ "Has escollit la dificultat fàcil, el teu personatge començarà amb els punts al màxim."
					+ Colors.RESET);
			ConsoleUtils.dormirSegons(3);
			finalitzar = true;

		}

		while (!finalitzar) {
			mostrarAtributs(personatge);

			System.out.println(Estils.TITOL + "=== Repartiment de punts ===" + Colors.RESET);
			System.out.println("Punts disponibles: " + personatge.getPuntsDisponibles() + "\n");

			System.out.println(Estils.PREGUNTA + "Quin atribut vols millorar? (V/A/G/F | Q Per sortir)" + Colors.RESET);
			System.out.println(Colors.VIDA + "  V. Vida");
			System.out.println(Colors.ATAC + "  A. Atac");
			System.out.println(Colors.AGILITAT + "  G. Agilitat");
			System.out.println(Colors.FORSA + "  F. Força");
			System.out.print(Estils.RESPOSTA);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;

			char opcio = entrada.charAt(0);

			// validar las letras
			if (opcio == 'Q') {
				finalitzar = true;
				continue;
			}

			// convertir lletra a atribut enum
			Atributs stat = Atributs.desdeLletra(opcio);

			if (stat == null) {
				System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
				continue;
			}

			// si no hay punts disponibles
			// asi q no cal seguir preguntando y mandar error
			if (personatge.getPuntsDisponibles() == 0) {
				System.out.println(Colors.GROC + "⚠ No tens punts disponibles!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
				continue;
			}

			// si la pregunta es correcta y hay puntos disponibles
			System.out.println(Estils.PREGUNTA + "Quants punts vols afegir a " + stat.name().toUpperCase() + "?");
			System.out.print(Estils.RESPOSTA);

			int cantidad;
			try {
				cantidad = Integer.parseInt(teclado.nextLine());

				if (cantidad <= 0) {
					System.out.println(Colors.VERMELL + "⚠ Introdueix un número enter positiu!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
					continue;
				}
			} catch (Exception e) {
				System.out.println(Colors.VERMELL + "⚠ Entrada invàlida!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
				continue;
			}

			if (cantidad > personatge.getPuntsDisponibles()) {
				System.out.println(Colors.VERMELL + "⚠ No tens tants punts!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
				continue;
			}

			if (!personatge.aplicarPunts(stat, cantidad)) {
				System.out.println(Colors.GROC + "⚠ No pots afegir " + cantidad + " a " + stat + "!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
			}
		}
	}

	public static void mostrarAtributs(Personatge personatge) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Atributs ===" + Colors.RESET);
		mostrarBarra("Vida    ", Colors.VIDA, personatge.getVida(), Atributs.VIDA.getMaxim(), 50, true);
		mostrarBarra("Atac    ", Colors.ATAC, personatge.getAtac(), Atributs.ATAC.getMaxim(), 50, true);
		mostrarBarra("Agilitat", Colors.AGILITAT, personatge.getAgilitat(), Atributs.AGILITAT.getMaxim(), 50, true);
		mostrarBarra("Força   ", Colors.FORSA, personatge.getForsa(), Atributs.FORSA.getMaxim(), 50, true);
		System.out.println(Colors.RESET);
	}

	private static void mostrarBarra(String nom, String color, int valor, int maxValor, int midaBarra,
			boolean mostrarValors) {
		int plens = (int) Math.round((double) valor / maxValor * midaBarra);
		int buits = midaBarra - plens;

		String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
		String sufix = mostrarValors ? color + valor + "/" + maxValor + Colors.RESET : "";

		System.out.println(color + nom + " | " + Colors.RESET + "[" + barra + "] " + sufix);
	}

	// TODO: Implementar si hay un monstruo entonces atacar activado y depende de la
	// sala quitar opciones de canviar de sala
	// Se puede implementar un sistema antes de si hay monstruo salga esta pantalla
	// pantalla

	public static void triarQueFer(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		masmorra.mostrarMasmorra(masmorra.getPersonatge());

		boolean juegoIniciado = true;
		while (juegoIniciado) {
			System.out.println(Estils.TITOL + "=== Què vols fer? ===" + Colors.RESET);
			System.out.println(Estils.PREGUNTA + "Tria una opció:" + Colors.RESET);
			System.out.println(Colors.VERD + "  M. Moure's per la masmorra");
			System.out.println(Colors.TARONJA + "  E. Explorar la sala actual");
			System.out.println(Colors.GROC + "  R. Mostrar atributs");
			System.out.println(Colors.BLAU + "  I. Mostrar inventari");
			System.out.println(Colors.VERMELL + "  Q. Sortir del joc" + Colors.RESET);
			System.out.print(Estils.RESPOSTA);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				return;

			char opcio = entrada.charAt(0);

			switch (opcio) {
				case 'M' -> moure(teclado, personatge, masmorra);
				case 'E' -> explorarSala();
				case 'R' -> {
					mostrarAtributs(personatge);
				}
				case 'I' -> inventari(personatge);
				case 'Q' -> {
					System.out.println(Colors.VERMELL + "Fins aviat!" + Colors.RESET);
					juegoIniciado = false;
					System.exit(0);
				}
				default -> {
					System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
					ConsoleUtils.saltarPagina();

				}
			}

		}

	}

	public static void combatre(Scanner teclado, Personatge personatge, Monstre monstre, Sala sala) {
		boolean combatActiu = true;

		while (combatActiu && personatge.potLluitar(monstre)) {

			mostrarMenuCombat(personatge, monstre);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;
			char opcio = entrada.charAt(0);

			switch (opcio) {
				case 'A' -> {
					atacar(personatge, monstre);

					// Si el monstre segueix viu, contraataca
					if (monstre.estaViu()) {
						int danyMonstre = monstre.calcularAtac();
						personatge.rebreDany(danyMonstre);
						System.out.println(Colors.VERMELL + monstre.getNom()
								+ " t'ha contraatacat per " + danyMonstre + " de dany." + Colors.RESET);
						System.out.println(Colors.VIDA + "La teva vida: " + personatge.getVida() + Colors.RESET);
						ConsoleUtils.dormirSegons(1.5);
					}

					if (!monstre.estaViu()) {
						ConsoleUtils.saltarPagina(Estils.TITOL + "=== Victòria! ===" + Colors.RESET);
						System.out.println(Colors.VERD + "Has derrotat al " + monstre.getNom() + "!" + Colors.RESET);
						personatge.sumarExperiencia(monstre.getValorExperiencia());
						System.out.println(
								Colors.GROC + "Has guanyat " + monstre.getValorExperiencia() + " XP!" + Colors.RESET);
						ConsoleUtils.dormirSegons(2);
						combatActiu = false;
					}

					if (!personatge.estaViu()) {
						ConsoleUtils.saltarPagina(Estils.TITOL + "=== Has mort! ===" + Colors.RESET);
						System.out.println(
								Colors.VERMELL + "Has estat derrotat per " + monstre.getNom() + "..." + Colors.RESET);
						ConsoleUtils.dormirSegons(3);
						System.exit(0);
					}
				}
				case 'F' -> {
					boolean fuita = fugir(personatge, monstre);
					if (fuita)
						combatActiu = false;

					// Si la fuita falla i el personatge ha mort
					if (!personatge.estaViu()) {
						ConsoleUtils.saltarPagina(Estils.TITOL + "=== Has mort! ===" + Colors.RESET);
						System.out.println(Colors.VERMELL + "Has mort intentant fugir de " + monstre.getNom() + "..."
								+ Colors.RESET);
						ConsoleUtils.dormirSegons(3);
						System.exit(0);
					}
				}
				default -> {
					System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1);
				}
			}
		}
	}

	public static void moure(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		masmorra.mostrarMasmorra(masmorra.getPersonatge());

		System.out.println(Estils.TITOL + "=== Moure's per la masmorra ===" + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "En quina direcció vols moure't? (W/A/S/D)" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);

		String entrada = teclado.nextLine().toUpperCase();
		if (entrada.isEmpty())
			return;

		char opcio = entrada.charAt(0);

		switch (opcio) {
			case 'W' -> personatge.moure('W', masmorra.getX());
			case 'A' -> personatge.moure('A', masmorra.getX());
			case 'S' -> personatge.moure('S', masmorra.getY());
			case 'D' -> personatge.moure('D', masmorra.getY());
			default -> System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
		}
		masmorra.mostrarMasmorra(masmorra.getPersonatge());

	}

	public static void explorarSala() {
		int max = 5;

		for (int min = 0; min < max; min++) {
			ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);

			mostrarBarra("Explorant la sala...", Colors.TARONJA, min, max, 30, false);
			ConsoleUtils.dormirSegons(1);
		}

		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);
		System.out.println("Sala Explorada");

		System.out.println("No hi ha res d'interessant aquí.");
		ConsoleUtils.dormirSegons(2);
	}

	public static void inventari(Personatge personatge) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Inventari ===" + Colors.RESET);

		Tresor[] equipament = personatge.getEquipament();
		boolean hiHaTresors = false;

		System.out.println(Estils.TITOL + "  Tresors:" + Colors.RESET);
		for (int i = 0; i < equipament.length; i++) {
			if (equipament[i] != null) {
				hiHaTresors = true;
				System.out.printf("  %s[%d]%s %s — %s%d g%s — %s%d or%s%n",
						Colors.GRIS, i + 1, Colors.RESET,
						Colors.BLANC + equipament[i].getNom() + Colors.RESET,
						Colors.GROC, (int) equipament[i].getPes(), Colors.RESET,
						Colors.TARONJA, equipament[i].getValor(), Colors.RESET);
			}
		}
		if (!hiHaTresors) {
			System.out.println(Colors.GRIS + "  No tens cap tresor." + Colors.RESET);
		}

		System.out.println();
		ConsoleUtils.dormirSegons(3);
		ConsoleUtils.saltarPagina();
	}

	public static boolean fugir(Personatge personatge, Monstre monstre) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Intentant fugir... ===" + Colors.RESET);

		boolean escapada = personatge.ferTiradaAgilitat();

		if (escapada) {
			System.out.println(Colors.VERD + "✔ Has aconseguit fugir!" + Colors.RESET);
			ConsoleUtils.dormirSegons(2);
		} else {
			// El monstre aprofita i ataca
			int dany = monstre.calcularAtac();
			personatge.rebreDany(dany);
			System.out.println(Colors.VERMELL + "✘ No has pogut fugir! El monstre t'ha atacat per " + dany + " de dany."
					+ Colors.RESET);
			System.out.println(Colors.VIDA + "Vida restant: " + personatge.getVida() + Colors.RESET);
			ConsoleUtils.dormirSegons(2.5);
			ConsoleUtils.saltarPagina();
		}

		return escapada;
	}

	public static void atacar(Personatge personatge, Monstre monstre) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Atacant! ===" + Colors.RESET);

		int dany = personatge.atacar(monstre);
		System.out.println(Colors.ATAC + "Has atacat a " + monstre.getNom()
				+ " per " + dany + " de dany!" + Colors.RESET);
		System.out.println(Colors.VIDA + "Vida del monstre: " + monstre.getVida() + Colors.RESET);
		ConsoleUtils.dormirSegons(1.5);
		ConsoleUtils.saltarPagina();
	}


	public static void mostrarMenuCombat(Personatge personatge, Monstre monstre) {
		ConsoleUtils.saltarPagina("Has entrat a una sala amb un monstre!");
		ConsoleUtils.dormirSegons(1.5);
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Atributs en combate ===" + Colors.RESET);
		System.out.println(Colors.VERMELL + "Monstre: " + monstre.getNom());
		System.out.println(Colors.VIDA + "Vida del monstre: " + monstre.getVida() + Colors.RESET);
		System.out.println();
		System.out.println(Estils.TITOL + "=== Opcions de combate ===" + Colors.RESET);
		System.out.println("A. Atacar");
		System.out.println("F. Fugir");
		System.out.print(Estils.PREGUNTA + "Què vols fer?" + Colors.RESET);
	}
}