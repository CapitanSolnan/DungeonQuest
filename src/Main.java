import java.util.Scanner;
import model.Personatge;
import utils.Colors;
import utils.ConsoleUtils;
import utils.MathUtils;

public class Main {
	public static void main(String[] args) {
		// TODO: Empezar partida
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina(); // neteja inicial per alinear vista consola

		imprimirMissatgeBenvinguda(teclado);

		// demanar el nom del jugador
		String nom = demanarNom(teclado);

		int vida = 5;
		int atac = 1;
		int agilitat = 4;
		int forsa = 4;

		// ELEGIR DIFICULTAD
		int dificultat = escollirDificultat(teclado);

		// MOSTRAR STATS INICIALES
		mostrarStats(vida, atac, agilitat, forsa);

		// System.out.println("\nDistribuïu els " + puntos + " PUNTS");
		// System.out.println("Els punts NO asignats NO es podran RECUPERAR");

		// REPARTIR PUNTOS
		// while (puntos > 0) {

		// System.out.println("\nPunts disponibles: " + puntos);
		// System.out.println("Quina estadística vols millorar? (" + Colors.VIDA + "vida
		// " + Colors.RESET + "/" + Colors.ATAC
		// + " atac " + Colors.RESET + "/" + Colors.AGILITAT + " agilitat " +
		// Colors.RESET + "/" + Colors.FORSA + "forsa"
		// + Colors.RESET + ")");
		// String opcio = teclado.nextLine().toLowerCase();

		// System.out.println("Quants punts vols afegir?");
		// int cantidad = Integer.parseInt(teclado.nextLine());

		// int[] resultat = repartirMultiplesPuntos(opcio, cantidad, vida, atac,
		// agilitat, forsa, puntos);

		// vida = resultat[0];
		// atac = resultat[1];
		// agilitat = resultat[2];
		// forsa = resultat[3];

		// puntos -= resultat[4];
		// }

		// mostrarStats(vida, atac, agilitat, forsa);

		// Personatge player1 = new Personatge(nom, vida, atac, agilitat, forsa);
		// System.out.println(player1);
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

	public static int[] repartirMultiplesPuntos(String opcio, int cantidad, int vida, int atac, int agilitat, int forsa,
			int puntosDisponibles) {

		int puntosGastados = 0;

		if (cantidad <= 0) {
			System.out.println("Has d'afegir almenys 1 punt!");
			return new int[] { vida, atac, agilitat, forsa, 0 };
		}

		if (cantidad > puntosDisponibles) {
			System.out.println("No tens tants punts!");
			return new int[] { vida, atac, agilitat, forsa, 0 };
		}

		if (opcio.equals("vida")) {
			int max = 20 - vida;
			if (max > 0) {
				int aSumar = Math.min(cantidad, max);
				vida += aSumar;
				puntosGastados = aSumar;
			} else {
				System.out.println("Vida al màxim!");
			}

		} else if (opcio.equals("atac")) {
			int max = 4 - atac;
			if (max > 0) {
				int aSumar = Math.min(cantidad, max);
				atac += aSumar;
				puntosGastados = aSumar;
			} else {
				System.out.println("Atac al màxim!");
			}

		} else if (opcio.equals("agilitat")) {
			int max = 11 - agilitat;
			if (max > 0) {
				int aSumar = Math.min(cantidad, max);
				agilitat += aSumar;
				puntosGastados = aSumar;
			} else {
				System.out.println("Agilitat al màxim!");
			}

		} else if (opcio.equals("forsa")) {
			int max = 11 - forsa;
			if (max > 0) {
				int aSumar = Math.min(cantidad, max);
				forsa += aSumar;
				puntosGastados = aSumar;
			} else {
				System.out.println("Forsa al màxim!");
			}

		} else {
			System.out.println("Opció no vàlida!");
		}

		return new int[] { vida, atac, agilitat, forsa, puntosGastados };
	}

	public static void mostrarStats(int vida, int atac, int agilitat, int forsa) {
		System.out.println("\n=== Estadístiques ===");
		System.out.println(Colors.VIDA + "VIDA     | " + vida + " | MAX 20" + Colors.RESET);
		System.out.println(Colors.ATAC + "ATAC     | " + atac + " | MAX 4" + Colors.RESET);
		System.out.println(Colors.AGILITAT + "AGILITAT | " + agilitat + " | MAX 11" + Colors.RESET);
		System.out.println(Colors.FORSA + "FORSA    | " + forsa + " | MAX 11" + Colors.RESET);
		System.out.println();
	}

}
