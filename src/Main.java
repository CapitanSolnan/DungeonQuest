import java.util.Scanner;

import model.Personatge;
import utils.Colors;
import utils.ConsoleUtils;

public class Main {
	public static void main(String[] args) {
		// TODO: Empezar partida
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina(); // neteja inicial per alinear vista consola

		imprimirMissatgeBenvinguda(teclado);

		// demanar el nom del jugador
		String nom = demanarNom(teclado);

		// escollir dificultat
		int dificultat = escollirDificultat(teclado);

		// crear personatge
		Personatge personatge = new Personatge(nom, dificultat);

		repartirPunts(teclado, personatge);

		// demanar mida masmorra
		int[] midaMasmorra = demanarMidaMasmorra(teclado);

		// crear objecte masmorra
		Masmorra masmorra = new Masmorra(midaMasmorra[0], midaMasmorra[1], personatge);

		masmorra.mostrarMasmorra(masmorra.personatge);
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
				Colors.TITOL + " === Creació de personatge === " + Colors.RESET);
		System.out.println(Colors.PREGUNTA + "Trieu un nom per al vostre jugador: ");
		System.out.print(Colors.RESPOSTA);
		String nom = teclado.nextLine();
		return nom;
	}

	public static int escollirDificultat(Scanner teclado) {
		int dificultat = 0;
		boolean esDificultatValida = false;

		do {
			ConsoleUtils.saltarPagina(Colors.TITOL + "=== Escollir dificultat ===" + Colors.RESET);

			System.out.println("  " + Colors.VERD + "1. Fàcil");
			System.out.println("  " + Colors.GROC + "2. Normal");
			System.out.println("  " + Colors.VERMELL + "3. Difícil" + Colors.RESET);

			System.out.println(Colors.PREGUNTA + "Quina dificultat vols? (1-3): ");
			System.out.print(Colors.RESPOSTA);

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
				ConsoleUtils.saltarPagina(Colors.TITOL + "=== Mida de la masmorra ===" + Colors.RESET);

				// mostrar X si estem preguntant Y
				if (i == 1) {
					System.out.println("X seleccionada: " + Colors.VERD + mides[0] + Colors.RESET + "\n");
				}

				try {
					System.out.println(Colors.PREGUNTA + "Introdueix " + eixos[i] + ": ");
					System.out.print(Colors.RESPOSTA);
					int valor = Integer.parseInt(teclado.nextLine());

					if (valor >= Masmorra.MIN_MIDA_MASMORRA && valor <= Masmorra.MAX_MIDA_MASMORRA) {
						mides[i] = valor;
						valid = true;
					} else {
						System.out.println(Colors.VERMELL + "⚠ La mida ha de ser entre " + Masmorra.MIN_MIDA_MASMORRA + " i "
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

	public static void repartirPunts(Scanner teclado, Personatge personatge) {
		boolean finalitzar = false;

		while (!finalitzar) {
			mostrarAtributs(personatge);

			System.out.println(Colors.TITOL + "=== Repartiment de punts ===" + Colors.RESET);
			System.out.println("Punts disponibles: " + personatge.getPuntsDisponibles() + "\n");

			System.out.println(Colors.PREGUNTA + "Quin atribut vols millorar? (V/A/G/F | Q Per sortir)" + Colors.RESET);
			System.out.println(Colors.VIDA + "  V. Vida");
			System.out.println(Colors.ATAC + "  A. Atac");
			System.out.println(Colors.AGILITAT + "  G. Agilitat");
			System.out.println(Colors.FORSA + "  F. Força");
			System.out.print(Colors.RESPOSTA);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;

			char opcio = entrada.charAt(0);

			// validar las letras
			if (opcio == 'Q') {
				finalitzar = true;
				continue;
			}

			String stat;

			switch (opcio) {
				case 'V':
					stat = "vida";
					break;
				case 'A':
					stat = "atac";
					break;
				case 'G':
					stat = "agilitat";
					break;
				case 'F':
					stat = "forsa";
					break;
				default:
					stat = null;
					break;
			}

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
			System.out.println(Colors.PREGUNTA + "Quants punts vols afegir a " + stat.toUpperCase() + "?");
			System.out.print(Colors.RESPOSTA);

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
		ConsoleUtils.saltarPagina(Colors.TITOL + "=== Atributs ===" + Colors.RESET);
		mostrarBarra("Vida    ", Colors.VIDA, personatge.getVida(), Personatge.MAX_VIDA);
		mostrarBarra("Atac    ", Colors.ATAC, personatge.getAtac(), Personatge.MAX_ATAC);
		mostrarBarra("Agilitat", Colors.AGILITAT, personatge.getAgilitat(), Personatge.MAX_AGILITAT);
		mostrarBarra("Força   ", Colors.FORSA, personatge.getForsa(), Personatge.MAX_FORSA);
		System.out.println(Colors.RESET);
	}

	private static void mostrarBarra(String nom, String color, int valor, int maxValor) {
		final int MIDA_BARRA = 50;
		int plens = (int) Math.round((double) valor / maxValor * MIDA_BARRA);
		int buits = MIDA_BARRA - plens;

		String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
		System.out.println(
				color + nom + " | " + Colors.RESET + "[" + barra + "] " + color + valor + "/" + maxValor + Colors.RESET);
	}
}
