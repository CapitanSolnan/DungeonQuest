import java.util.ArrayList;
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
import sala.SalaJefe;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import utils.Missatges;

public class Main {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina(); // neteja inicial per alinear vista consola

		boolean debug = Missatges.imprimirMissatgeBenvinguda(teclado);

		// demanar el nom del jugador
		String nom = (debug) ? "" : demanarNom(teclado);

		// escollir dificultat
		Dificultats dificultat = (debug) ? Dificultats.NORMAL : demanarDificultat(teclado);

		// crear personatge
		Personatge personatge = new Personatge(nom, dificultat);
		if (!debug) {
			repartirPuntsInicials(teclado, personatge, dificultat);
		}

		// demanar mida masmorra
		int[] midaMasmorra = (debug) ? new int[] { 10, 10 } : demanarMidaMasmorra(teclado);

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

	public static void logicaJoc(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		boolean jocFinalizat = false;
		while (!jocFinalizat) {
			if (!personatge.estaViu()) {
				mostrarGameOver(personatge, masmorra);
				jocFinalizat = true;
				continue;
			}

			ConsoleUtils.saltarPagina();
			mostrarMapaAmbStats(personatge, masmorra);
			Accions seguentAccio = demanarSeguentAccio(teclado, personatge);

			switch (seguentAccio) {
				case MOURE -> {
					boolean esMou = demanarMoviment(teclado, personatge, masmorra);
					if (!esMou) {
						continue;
					}
					Sala sala = masmorra.getSalaActual();

					// sala normal i té monstre
					if (!(sala instanceof SalaJefe) && sala.teMonstre()) {
						System.out.println(Colors.VERMELL + Estils.NEGRETA
								+ "⚠ Has entrat a una sala amb un " + sala.getMonstre().getNom() + "!"
								+ Colors.RESET);
						ConsoleUtils.dormirSegons(1.5);
						combatre(teclado, personatge, sala.getMonstre(), sala);
					}
					if (sala instanceof SalaJefe) {
						// TODO: Logica jefe
					}
				}
				case EXPLORAR -> explorarSala(teclado, personatge, masmorra);
				case OBRIR_INVENTARI -> demanarObrirInventari(personatge);
				case FICAR_PUNTS -> repartirPunts(teclado, personatge);
				case SORTIR -> jocFinalizat = true;
				case null -> {
					System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
					ConsoleUtils.dormirSegons(1.5);
				}
			}
		}
	}

	public static Accions demanarSeguentAccio(Scanner teclado, Personatge personatge) {
		System.out.println();
		System.out.println(Estils.TITOL + "=== Què vols fer? ===" + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "Tria una opció:" + Colors.RESET);
		System.out.println(Colors.VERD + "  M. Moure's per la masmorra");
		System.out.println(Colors.TARONJA + "  E. Explorar la sala actual");
		System.out.println(Colors.BLAU + "  I. Mostrar inventari");
		if (personatge.getPuntsDisponibles() > 0) {
			System.out.println(Colors.GROC + "  H. Repartir punts d'atributs" + Colors.RESET);

		}
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
			case 'h' -> (personatge.getPuntsDisponibles() > 0) ? Accions.FICAR_PUNTS : null;
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

	public static boolean combatre(Scanner teclado, Personatge personatge, Monstre monstre, Sala sala) {
		boolean combatActiu = true;
		boolean victoria = false;

		while (combatActiu && personatge.potLluitar(monstre)) {
			ConsoleUtils.saltarPagina();
			Missatges.mostrarMenuCombat(personatge, monstre);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;
			char opcio = entrada.charAt(0);

			switch (opcio) {
				case 'A' -> {
					int danyFet = personatge.atacar(monstre);
					System.out.println();
					System.out.println(Colors.TARONJA + "Has atacat el " + monstre.getNom()
							+ " i has fet " + danyFet + " de dany!" + Colors.RESET);

					if (!monstre.estaViu()) {
						System.out.println(Colors.VERD + Estils.NEGRETA + "Has derrotat el "
								+ monstre.getNom() + "!" + Colors.RESET);
						personatge.sumarExperiencia(monstre.getValorExperiencia());
						System.out.println(Colors.CIAN + "  +" + monstre.getValorExperiencia()
								+ " experiència" + Colors.RESET);
						sala.setMonstre(null); // eliminar monstre de la sala
						combatActiu = false;
						victoria = true;
					} else {
						int danyRebut = monstre.calcularAtac();
						personatge.rebreDany(danyRebut);
						System.out.println(Colors.VERMELL + "El " + monstre.getNom()
								+ " t'ha atacat i has rebut " + danyRebut + " de dany!" + Colors.RESET);

						if (!personatge.estaViu()) {
							System.out.println(Colors.VERMELL + Estils.NEGRETA
									+ "Has mort en combat!" + Colors.RESET);
							combatActiu = false;
						}
					}
					ConsoleUtils.dormirSegons(1.5);
				}
				case 'F' -> {
					// TODO: OPCION ESCAPAR
				}
				default -> System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
			}
		}
		return victoria;
	}

	public static void logicaSalaJefe(Scanner teclado, Personatge personatge, Masmorra masmorra, SalaJefe sala) {
		Monstre jefe = sala.getMonstre();
		ConsoleUtils.saltarPagina(Colors.VERMELL + Estils.NEGRETA);

		if (jefe.getNom().equalsIgnoreCase("Capitan Solnan")) {
			System.out.println("  ╔═════════════════════════════════════╗");
			System.out.println("  ║     !! SALA DE CAPITAN SOLNAN !!    ║");
			System.out.println("  ╚═════════════════════════════════════╝");
		} else {
			System.out.println("  ╔════════════════════════════════╗");
			System.out.println("  ║     !! SALA DE STAGEDDAT !!    ║");
			System.out.println("  ╚════════════════════════════════╝");
		}

		System.out.println(Colors.RESET);
		System.out.println(Colors.VERMELL + jefe.getNom() + " et talla el pas!");
		System.out.println("No pots sortir fins que l'hagis derrotat..." + Colors.RESET);
		ConsoleUtils.dormirSegons(3);

		boolean combatActiu = true;
		boolean victoria = false;

		while (combatActiu && personatge.potLluitar(jefe)) {
			ConsoleUtils.saltarPagina();
			Missatges.mostrarMenuCombatJefe(personatge, jefe);

			String entrada = teclado.nextLine().toUpperCase();
			if (entrada.isEmpty())
				continue;
			char opcio = entrada.charAt(0);

			switch (opcio) {
				case 'A' -> {
					int danyFet = personatge.atacar(jefe);
					System.out.println();
					System.out.println(Colors.TARONJA + "Ataques a " + jefe.getNom()
							+ " i fas " + danyFet + " de dany!" + Colors.RESET);

					if (!jefe.estaViu()) {
						System.out.println(Colors.VERD + Estils.NEGRETA);
						System.out.println("  ╔═════════════════════════════════╗");
						System.out.println("  ║       !! VICTÒRIA FINAL !!      ║");
						System.out.println("  ╚═════════════════════════════════╝");
						System.out.println(Colors.RESET);
						personatge.sumarExperiencia(jefe.getValorExperiencia());
						System.out.println(Colors.CIAN + "  +" + jefe.getValorExperiencia()
								+ " experiència!" + Colors.RESET);

						if (sala.getTresor() != null) {
							personatge.intentarAfegirTresor(sala.getTresor());
							System.out.println(Colors.GROC + "  Has obtingut: "
									+ sala.getTresor().getNom() + "!" + Colors.RESET);
						}
						sala.setMonstre(null);
						sala.setExplorada(true);
						combatActiu = false;
						victoria = true;
					} else {
						int danyRebut = jefe.calcularAtac();
						personatge.rebreDany(danyRebut);
						System.out.println(Colors.VERMELL + jefe.getNom()
								+ " t'ataca i reps " + danyRebut + " de dany!"
								+ Colors.RESET);
						if (!personatge.estaViu()) {
							System.out.println(Colors.VERMELL + Estils.NEGRETA + jefe.getNom() + " t'ha derrotat!" + Colors.RESET);
							combatActiu = false;
						}
					}
					ConsoleUtils.dormirSegons(1.5);
				}
				case 'F' -> {
					// TODO: OPCION ESCAPAR pero imposible
				}
				default -> System.out.println(Colors.VERMELL + "⚠ Opció invàlida!" + Colors.RESET);
			}
		}

		if (victoria) {
			ConsoleUtils.dormirSegons(2);
			mostrarVictoriaFinal(personatge, masmorra);
		}
	}

	/**
	 * Demanar moviment al jugador.
	 * 
	 * @param teclado
	 * @param personatge
	 * @param masmorra
	 * @return True en cas possible moviment, false en cas contrari.
	 */
	public static boolean demanarMoviment(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		ConsoleUtils.saltarPagina();
		mostrarMapaAmbStats(personatge, masmorra);

		System.out.println("\n" + Estils.TITOL + "=== Moure per la masmorra ===" + Colors.RESET);
		System.out.println(Estils.PREGUNTA + "En quina direcció vols moure't? (W/A/S/D)" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);

		String entrada = teclado.nextLine().toUpperCase();
		if (entrada.isEmpty())
			return false;

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
			return false;
		}

		int[] desti = masmorra.calcularNovaPosicio(personatge.getPosicio(), direccio);

		if (desti != null) {
			personatge.moure(desti);
			return true;
		} else {
			System.out.println(Colors.VERMELL + "⚠ No pots sortir de la masmorra!" + Colors.RESET);
			ConsoleUtils.dormirSegons(1);
			return false;
		}
	}

	public static void explorarSala(Scanner teclado, Personatge personatge, Masmorra masmorra) {
		Sala sala = masmorra.getSalaActual();

		if (sala.teMonstre()) {
			System.out.println(Colors.VERMELL + "⚠ No pots explorar amb un monstre present!" + Colors.RESET);
			System.out.println("Derrota'l primer!");
			ConsoleUtils.dormirSegons(2);
			if (masmorra.esSalaJefe()) {
				logicaSalaJefe(teclado, personatge, masmorra, (SalaJefe) sala);
			} else {
				combatre(teclado, personatge, sala.getMonstre(), sala);
			}
			return;
		}

		if (sala.estaExplorada()) {
			ConsoleUtils.saltarPagina(Estils.TITOL + "=== Sala explorada ===" + Colors.RESET);
			System.out.println(Colors.GRIS + "Ja has explorat aquesta sala." + Colors.RESET);
			ConsoleUtils.dormirSegons(1);
			return;
		}

		int max = 20;
		for (int min = 0; min < max; min++) {
			ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);
			Missatges.mostrarBarra("Explorant la sala...", Colors.TARONJA, min, max, 30, false);
			ConsoleUtils.dormirSegons(0.1);
		}

		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Sala explorada ===" + Colors.RESET);
		personatge.explorar(sala);
		masmorra.sumarSalesExplorades();

		if (sala.getTresor() != null) {
			System.out.println(Colors.GROC + "Has trobat un tresor: "
					+ sala.getTresor().getNom() + "!" + Colors.RESET);
			personatge.sumarExperiencia(5);
		} else {
			System.out.println(Colors.GRIS + "No hi ha res d'interessant aquí." + Colors.RESET);
			personatge.sumarExperiencia(1);
		}
		ConsoleUtils.dormirSegons(1);
	}

	public static void demanarObrirInventari(Personatge personatge) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Inventari ===" + Colors.RESET);

		ArrayList<Tresor> equipament = personatge.getEquipament();

		if (equipament.isEmpty() || equipament == null) {
			System.out.println(Colors.GRIS + "  No tens cap tresor." + Colors.RESET);
		} else {
			System.out.println(Colors.GRIS + "  Tresors:" + Colors.RESET);

			int i = 1;
			for (Tresor tresor : equipament) {
				String index = String.format("%s[%d]%s", Colors.GRIS, i++, Colors.RESET);
				String nom = Colors.BLANC + tresor.getNom() + Colors.RESET;
				String pes = String.format("%s%.1f g%s", Colors.GROC, tresor.getPes(), Colors.RESET);
				String valor = String.format("%s%d or%s", Colors.TARONJA, tresor.getValor(), Colors.RESET);

				System.out.println("  " + index + " " + nom + " - " + pes + " - " + valor);
				i++;
			}
		}

		System.out.println();
		ConsoleUtils.dormirSegons(3);
		ConsoleUtils.saltarPagina();
	}

	public static void mostrarGameOver(Personatge personatge, Masmorra masmorra) {
		ConsoleUtils.saltarPagina();
		System.out.println(Colors.VERMELL + Estils.NEGRETA);
		System.out.println("  ╔═════════════════════════════════╗");
		System.out.println("  ║          GAME  OVER             ║");
		System.out.println("  ╚═════════════════════════════════╝");
		System.out.println(Colors.RESET);
		System.out.println(Colors.GRIS + "  Heroi: " + Colors.BLANC + personatge.getNom());
		System.out.println(Colors.GRIS + "  Nivell assolit: " + Colors.BLANC + personatge.getLevel());
		System.out.println(Colors.GRIS + "  Experiència: " + Colors.CIAN + personatge.getExperiencia());
		System.out.println(Colors.GRIS + "  Sales explorades: " + Colors.BLANC
				+ masmorra.getPercentatgeSalesExplorades() + "%" + Colors.RESET);
		System.out.println();
	}

	public static void mostrarVictoriaFinal(Personatge personatge, Masmorra masmorra) {
		ConsoleUtils.saltarPagina();
		System.out.println(Colors.GROC + Estils.NEGRETA);
		System.out.println("  ╔═════════════════════════════════╗");
		System.out.println("  ║           HAS GUANYAT!          ║");
		System.out.println("  ╚═════════════════════════════════╝");
		System.out.println(Colors.RESET);
		System.out.println(Colors.GRIS + "  Heroi: " + Colors.BLANC + personatge.getNom());
		System.out.println(Colors.GRIS + "  Nivell assolit: " + Colors.BLANC + personatge.getLevel());
		System.out.println(Colors.GRIS + "  Experiència: " + Colors.CIAN + personatge.getExperiencia());
		System.out.println(Colors.GRIS + "  Sales explorades: " + Colors.BLANC
				+ masmorra.getPercentatgeSalesExplorades() + "%" + Colors.RESET);
		System.out.println();
		ConsoleUtils.dormirSegons(5);
	}
}