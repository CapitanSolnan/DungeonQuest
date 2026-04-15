import java.util.Scanner;

import core.Dificultats;
import core.Masmorra;
import model.Personatge;
import model.Tresor;
import model.Accions;
import model.Atributs;
import model.Direccions;
import model.Monstre;
import sala.Sala;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import utils.Missatges;

public class Main {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina(); // neteja inicial per alinear vista consola

		Missatges.imprimirMissatgeBenvinguda(teclado);

		// demanar el nom del jugador
		String nom = demanarNom(teclado);

		// escollir dificultat
		Dificultats dificultat = demanarDificultat(teclado);

		// crear personatge
		Personatge personatge = new Personatge(nom, dificultat);

		repartirPuntsInicials(teclado, personatge, dificultat);

		// demanar mida masmorra
		int[] midaMasmorra = demanarMidaMasmorra(teclado);

		// crear objecte masmorra
		Masmorra masmorra = new Masmorra(midaMasmorra[0], midaMasmorra[1], personatge);
		logicaJoc(teclado, personatge, masmorra);
	}

	private static String demanarNom(Scanner teclado) {
		ConsoleUtils.saltarPagina(
				Estils.TITOL + " === Creació de personatge === " + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "Trieu un nom per al vostre jugador: ");
		System.out.print(Estils.RESPOSTA);
		String nom = teclado.nextLine();
		return nom;
	}

	public static Dificultats demanarDificultat(Scanner teclado) {
		Dificultats dificultatEscollida = null;

		while (dificultatEscollida == null) {
			Missatges.menuDificultats();

			try {
				int opcio = Integer.parseInt(teclado.nextLine());
				dificultatEscollida = Dificultats.desdeNumero(opcio);

				if (dificultatEscollida == null) {
					System.out.println(Colors.VERMELL + "⚠ Opció no vàlida!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
				}
			} catch (NumberFormatException e) {
				System.out.println(Colors.VERMELL + "⚠ Introdueix un número vàlid!" + Colors.RESET);
				ConsoleUtils.dormirSegons(1.5);
			}
		}

		return dificultatEscollida;
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

	public static void repartirPuntsInicials(Scanner teclado, Personatge personatge, Dificultats dificultat) {
		if (dificultat == Dificultats.DIFICIL) {
			Missatges.mostrarAtributs(personatge);
			System.out.println(Estils.NEGRETA + Colors.VERMELL
					+ "Has escollit la dificultat difícil, el teu personatge començarà amb els punts al mínim."
					+ Colors.RESET);
			ConsoleUtils.dormirSegons(3);
			return;

		} else if (dificultat == Dificultats.FACIL) {
			personatge.setVida(20);
			personatge.setAtac(4);
			personatge.setAgilitat(11);
			personatge.setForsa(11);

			Missatges.mostrarAtributs(personatge);
			System.out.println(Estils.NEGRETA + Colors.VERMELL
					+ "Has escollit la dificultat fàcil, el teu personatge començarà amb els punts al màxim."
					+ Colors.RESET);
			ConsoleUtils.dormirSegons(3);
			return;
		} else {
			repartirPunts(teclado, personatge);
		}
	}

	public static void repartirPunts(Scanner teclado, Personatge personatge) {
		boolean finalitzar = false;

		while (!finalitzar) {
			Missatges.mostrarAtributs(personatge);

			Missatges.menuRepartirPunts(personatge);

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


	public static void mostrarMapaAmbStats(Personatge personatge, Masmorra masmorra) {
		String[] mapa = masmorra.generarLiniesMapa(personatge);
		String[] stats = Missatges.generarLiniesStats(personatge);

		int maxLinies = Math.max(mapa.length, stats.length);

		for (int i = 0; i < maxLinies; i++) {
			// calcular distancia mapa
			String columnaMapa = (i < mapa.length) ? mapa[i] : " ".repeat(masmorra.getY() * 5);

			// calcular si no hi ha stats
			String columnaStats = (i < stats.length) ? stats[i] : "";

			System.out.println(columnaMapa + "       " + columnaStats);
		}
		// calcular cierre?
		// System.out.println("_".repeat(masmorra.getX() * 5 + 25));
	}


	// TODO: Implementar si hay un monstruo entonces atacar activado y depende de la
	// sala quitar opciones de canviar de sala
	// Se puede implementar un sistema antes de si hay monstruo salga esta pantalla
	// pantalla

	public static void logicaJoc(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		boolean jocFinalizat = false;
		while (!jocFinalizat) {
			ConsoleUtils.saltarPagina();
			mostrarMapaAmbStats(personatge, masmorra);
			Accions seguentAccio = demanarSeguentAccio(teclado);

			switch (seguentAccio) {
				case MOURE -> demanarMoviment(teclado, personatge, masmorra);
				case EXPLORAR -> explorarSala();
				case OBRIR_INVENTARI -> demanarObrirInventari(personatge);
				case SORTIR -> jocFinalizat = true;
			}
		}
	}

	public static Accions demanarSeguentAccio(Scanner teclado) {
		System.out.println();
		System.out.println(Estils.TITOL + "=== Què vols fer? ===" + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "Tria una opció:" + Colors.RESET);
		System.out.println(Colors.VERD + "  M. Moure's per la masmorra");
		System.out.println(Colors.TARONJA + "  E. Explorar la sala actual");
		System.out.println(Colors.BLAU + "  I. Mostrar inventari");
		System.out.println(Colors.VERMELL + "  Q. Sortir del joc" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);

		String entrada = teclado.nextLine().toLowerCase();
		if (entrada.isEmpty())
			return null;

		char opcio = entrada.charAt(0);

		Accions accio = switch (opcio) {
			case 'm' -> Accions.MOURE;
			case 'e' -> Accions.EXPLORAR;
			case 'i' -> Accions.OBRIR_INVENTARI;
			case 'q' -> Accions.SORTIR;
			// System.out.println(Colors.VERMELL + "Fins aviat!" + Colors.RESET);
			// juegoIniciado = false;
			// System.exit(0);

			default -> null;
			// System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
			// ConsoleUtils.dormirSegons(1.5);
			// ConsoleUtils.saltarPagina();

		};
		return accio;
	}

	public static void combatre(Scanner teclado, Personatge personatge, Monstre monstre, Sala sala) {
		boolean combatActiu = true;

		while (combatActiu && personatge.potLluitar(monstre)) {

			Missatges.mostrarMenuCombat(personatge, monstre);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;
			char opcio = entrada.charAt(0);

			switch (opcio) {
				case 'A' -> demanarAtacar();
				case 'F' -> demanarFugir();
				default -> System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
			}

		}
	}

	public static void demanarMoviment(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		ConsoleUtils.saltarPagina();
		mostrarMapaAmbStats(personatge, masmorra);

		System.out.println("\n" + Estils.TITOL + "=== Moure per la masmorra ===" + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "En quina direcció vols moure't? (W/A/S/D)" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);

		String entrada = teclado.nextLine().toUpperCase();
		if (entrada.isEmpty())
			return;

		char opcio = entrada.charAt(0);

		Direccions direccio = switch (opcio) {
			case 'W' -> Direccions.NORD;
			case 'S' -> Direccions.SUD;
			case 'A' -> Direccions.OEST;
			case 'D' -> Direccions.EST;
			default -> null;
		};

		if (direccio == null) {
			System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
			ConsoleUtils.dormirSegons(1);
			return;
		}

		int[] desti = masmorra.calcularNovaPosicio(personatge.getPosicio(), direccio);

		if (desti != null) {
			personatge.moure(desti);
		} else {
			System.out.println(Colors.VERMELL + "⚠ No pots sortir de la masmorra!" + Colors.RESET);
			ConsoleUtils.dormirSegons(1);
		}
	}

	public static void explorarSala() {
		int max = 5;

		for (int min = 0; min < max; min++) {
			ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);

			Missatges.mostrarBarra("Explorant la sala...", Colors.TARONJA, min, max, 30, false);
			ConsoleUtils.dormirSegons(1);
		}

		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);
		System.out.println("Sala Explorada");

		System.out.println("No hi ha res d'interessant aquí.");
		ConsoleUtils.dormirSegons(2);
	}

	public static void demanarObrirInventari(Personatge personatge) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Inventari ===" + Colors.RESET);

		Tresor[] equipament = personatge.getEquipament();
		boolean hiHaTresors = false;

		System.out.println(Colors.GRIS + "  Tresors:" + Colors.RESET);
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

	public static void demanarAtacar() {

		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Atacar ===" + Colors.RESET);
		System.out.println("Atacant al monstre...");
		ConsoleUtils.dormirSegons(2);
	}

	public static void demanarFugir() {

		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Fugir ===" + Colors.RESET);
		System.out.println("Intentant fugir...");
		ConsoleUtils.dormirSegons(2);
	}


}