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

		// TODO: repartir punts
		repartirPunts(teclado, personatge);

		// demanar mida masmorra
		int[] midaMasmorra = demanarMidaMasmorra(teclado);
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

		while (personatge.getPuntsDisponibles() > 0) {
			mostrarStats(personatge);

			System.out.println(Colors.TITOL + "=== Repartiment de punts ===" + Colors.RESET);
			System.out.println("Punts disponibles: " + personatge.getPuntsDisponibles() + "\n");

			System.out.println(Colors.PREGUNTA + "Quina estadística vols millorar? (V/A/G/F | Q Per sortir)" + Colors.RESET);
			System.out.println(Colors.VIDA + "  V. VIDA");
			System.out.println(Colors.ATAC + "  A. ATAC");
			System.out.println(Colors.AGILITAT + "  G. AGILITAT");
			System.out.println(Colors.FORSA + "  F. FORÇA");
			System.out.print(Colors.RESPOSTA);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;

			char opcio = entrada.charAt(0);

			if (opcio == 'Q') {
				return;
			}

			System.out.println(Colors.PREGUNTA + "Quants punts vols afegir?");
			System.out.print(Colors.RESPOSTA);

			int cantidad;
			try {
				cantidad = Integer.parseInt(teclado.nextLine());

				if (cantidad <= 0) {
					System.out.println(Colors.VERMELL + "⚠ Introdueix un número positiu!" + Colors.RESET);
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

			switch (opcio) {
				case 'V':
					int puntsVida = calcularPuntsAplicables(personatge.getVida(), 20, cantidad, "Vida");

					if (puntsVida > 0) {
						personatge.setVida(personatge.getVida() + puntsVida);
						personatge.setPuntsDisponibles(personatge.getPuntsDisponibles() - puntsVida);
					}
					break;

				case 'A':
					int puntsAtac = calcularPuntsAplicables(personatge.getAtac(), 4, cantidad, "Atac");

					if (puntsAtac > 0) {
						personatge.setAtac(personatge.getAtac() + puntsAtac);
						personatge.setPuntsDisponibles(personatge.getPuntsDisponibles() - puntsAtac);
					}
					break;

				case 'G':
					int puntsAgilitat = calcularPuntsAplicables(personatge.getAgilitat(), 11, cantidad, "Agilitat");

					if (puntsAgilitat > 0) {
						personatge.setAgilitat(personatge.getAgilitat() + puntsAgilitat);
						personatge.setPuntsDisponibles(personatge.getPuntsDisponibles() - puntsAgilitat);
					}
					break;

				case 'F':
					int puntsForsa = calcularPuntsAplicables(personatge.getForsa(), 11, cantidad, "Força");

					if (puntsForsa > 0) {
						personatge.setForsa(personatge.getForsa() + puntsForsa);
						personatge.setPuntsDisponibles(personatge.getPuntsDisponibles() - puntsForsa);
					}
					break;

				default:
					System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
			}
		}

		mostrarStats(personatge);
		return;
	}

	public static int calcularPuntsAplicables(int valorActual, int maxim, int cantidad, String nomEstadistica) {

		if (valorActual >= maxim) {
			System.out.println(Colors.GROC + "⚠ " + nomEstadistica + " ja està al màxim!" + Colors.RESET);
			ConsoleUtils.dormirSegons(1.5);

			return 0;
		}

		if (valorActual + cantidad > maxim) {
			System.out.println(Colors.GROC + "⚠ No pots superar " + maxim + " en " + nomEstadistica + "!" + Colors.RESET);
			ConsoleUtils.dormirSegons(1.5);

			return 0;
		}

		return cantidad;
	}

	public static void mostrarStats(Personatge personatge) {
		ConsoleUtils.saltarPagina(Colors.TITOL + "=== Estadístiques ===" + Colors.RESET);
		System.out.println(Colors.VIDA + "VIDA     | " + personatge.getVida() + "/20");
		System.out.println(Colors.ATAC + "ATAC     | " + personatge.getAtac() + "/4");
		System.out.println(Colors.AGILITAT + "AGILITAT | " + personatge.getAgilitat() + "/11");
		System.out.println(Colors.FORSA + "FORÇA    | " + personatge.getForsa() + "/11" + Colors.RESET);
		System.out.println();
	}
}
