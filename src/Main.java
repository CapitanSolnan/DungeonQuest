import java.util.Scanner;
import model.Personatge;

public class Main {
	public static void main(String[] args) {
		// TODO: Empezar partida

		Scanner teclado = new Scanner(System.in);

		System.out.println("BENVINGUT/DA A DUNGEON QUEST!");
		System.out.println();

		// Demanar el nom del jugador
		String nom = demanarNom(teclado);
		System.out.println();

		int vida = 5;
		int atac = 1;
		int agilitat = 4;
		int forsa = 4;
		System.out.println();
		System.out.println("VIDA     | 5 | MAXIM DE 20");
		System.out.println("ATAC     | 1 | MAXIM DE 4");
		System.out.println("AGILITAT | 4 | MAXIM DE 11");
		System.out.println("FORSA    | 4 | MAXIM DE 11");
		System.out.println();

		System.out.println("ESCOLLEIX LA DIFICULTAT (Només varia el punts inicials)");
		System.out.println("FÀCIL | NORMAL | DIFÍCIL");
		String dificultat = teclado.nextLine();
		int puntos = 0;

		while (!dificultat.equalsIgnoreCase("facil") || !dificultat.equalsIgnoreCase("fàcil")
				|| !dificultat.equalsIgnoreCase("normal")
				|| !dificultat.equalsIgnoreCase("difícil") || !dificultat.equalsIgnoreCase("dificil")) {

			if (dificultat.equalsIgnoreCase("facil") || dificultat.equalsIgnoreCase("fàcil")) {
				puntos = 32;
				break;
			} else if (dificultat.equalsIgnoreCase("normal")) {
				puntos = 12;
				break;

			} else if (dificultat.equalsIgnoreCase("dificil") || dificultat.equalsIgnoreCase("difícil")) {
				System.out.println("No tens punts inicials");
				break;

			} else {
				System.out.println("Error al escollir la dificultat");

				System.out.println();
				System.out.println("FÀCIL | NORMAL | DIFÍCIL");
				dificultat = teclado.nextLine();
			}
		}

		if (puntos > 0) {
			int newVida = 0;
			int newAtac = 0;
			int newAgilitat = 0;
			int newForsa = 0;
			System.out.println("Distribuïu els " + puntos + " PUNTS");
			System.out.println("Els punts NO asignats NO es podran RECUPERAR");
			System.out.println();

			System.out.print("VIDA 5 + ");
			newVida = teclado.nextInt();
			System.out.println("VIDA: " + (vida + newVida));
			vida += newVida;
			if (puntos <= 0) {

			} else {
				System.out.println("Distribuïu els " + (puntos - newVida) + " PUNTS");

				System.out.print("ATAC 1 + ");
				newAtac = teclado.nextInt();

				System.out.println("ATAC: " + (atac + newAtac));
				atac += newAtac;
				if (puntos <= 0) {

				} else {
					System.out.println("Distribuïu els " + (puntos - newVida - newAtac) + " PUNTS");

					System.out.print("AGILITAT 4 + ");
					newAgilitat = teclado.nextInt();

					System.out.println("AGILITAT: " + (agilitat + newAgilitat));

					agilitat += newAgilitat;
					if (puntos <= 0) {

					} else {
						System.out.println("Distribuïu els " + (puntos - newVida - newAtac - newAgilitat) + " PUNTS");

						System.out.print("FORSA 4 + ");
						newForsa = teclado.nextInt();

						System.out.println("FORSA: " + (forsa + newForsa));
						forsa += newForsa;
					}
				}

			}

		}

		Personatge player1 = new Personatge(nom, vida, atac, agilitat, forsa);

	}

	private static String demanarNom(Scanner teclado) {
		System.out.println("Trieu un nom per al vostre jugador: ");
		String nom = teclado.nextLine();
		return nom;
	}

}
