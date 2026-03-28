
import java.util.Scanner;

import model.Personatge;

public class Masmorra {

	public static void tauler() {
		// Valor provisional
		int x = 10;
		int y = 10;
		char[][] matriu = new char[x][y];

		for (int fil = 0; fil < matriu.length; fil++) {
			for (int col = 0; col < matriu[fil].length; col++) {

				// S'HA ALEATORITZA EL TAULER//
				int random = (int) (Math.random() * 100) + 1;
				if (random <= 20) {
					matriu[fil][col] = 'P';// Sala pont
				} else if (random > 20 && random <= 40) {
					matriu[fil][col] = 'T';// Sala teranyina
				} else {
					matriu[fil][col] = 'C';// Sala comuna
				}

				// CREEACIÓ DE LA TAULA//
				System.out.print(matriu[fil][col] + "   | ");
			}
			System.out.println();
		}
	}

	public static void opcions(Personatge p) {
		Scanner teclado = new Scanner(System.in);
		if () {//Si hay un monstruo
			System.out.println("Que vols fer Explorar (E) / Atacar (A) / Moure (M)");
			String opcio = teclado.nextLine();
			if (opcio.equalsIgnoreCase("e") || opcio.equalsIgnoreCase("explorar")) {
				
			}else if (opcio.equalsIgnoreCase("a") || opcio.equalsIgnoreCase("atacar")) {
			
			}else if (opcio.equalsIgnoreCase("m") || opcio.equalsIgnoreCase("moure")) {
				System.out.print("Direcció del moviment: ");
				char direccio = teclado.next().charAt(0);
				p.moure(direccio);
			}else {
				System.out.println("Opció no vàlida");
			}
		}else {//Si no hay un monstruo
		System.out.println("Que vols fer Explorar (E) / Moure (M)");
		String opcio = teclado.nextLine();
		if (opcio.equalsIgnoreCase("e") || opcio.equalsIgnoreCase("explorar")) {
			
		}else if (opcio.equalsIgnoreCase("m") || opcio.equalsIgnoreCase("moure")) {
			System.out.print("Direcció del moviment: ");
			char direccio = teclado.next().charAt(0);
			p.moure(direccio);
		}else {
			System.out.println("Opció no vàlida");
		}
		}
	}

	public static void finalitzar(Personatge p) {
		boolean finalitzar = false;
		if (finalitzar) {
			System.out.println("");
		} else {
			System.out.println("Encara no s'ha finalitzat");
			if (p.vida == 0) {
				finalitzar = true;
				System.out.println("Partida Fallida");
				System.out.println("Experiencia obtenida: " + p.experiencia);
				if (montre) {// Causa de la mort MONSTRE

				} else {// Causa de la mort PONT // TIRADES DE DAU

				}
				System.out.println("Percentatge explorat: " + explPer);
			}
		}
	}

}
