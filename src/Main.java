import java.util.Scanner;
import model.Personatge;
import utils.MathUtils;

public class Main {
	public static void main(String[] args) {
		// TODO: Empezar partida

		Scanner teclado = new Scanner(System.in);

		System.out.print("Inserta nombre: ");
		String nom = teclado.nextLine();


		int vida = 5;
		int atac= 1;
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

		while (!dificultat.equalsIgnoreCase("facil") || !dificultat.equalsIgnoreCase("fàcil") || !dificultat.equalsIgnoreCase("normal") 
				|| !dificultat.equalsIgnoreCase("difícil") || !dificultat.equalsIgnoreCase("dificil" ) ) {

			if (dificultat.equalsIgnoreCase("facil") || dificultat.equalsIgnoreCase("fàcil")) {
				puntos = 32;
			}else if (dificultat.equalsIgnoreCase("normal")) {
				puntos = 12;
			}else if (dificultat.equalsIgnoreCase("dificil") || dificultat.equalsIgnoreCase("difícil")) {
				System.out.println("No tens punts inicials");
			}else {
				System.out.println("Error al escollir la dificultat");

				System.out.println();
				System.out.println("FÀCIL | NORMAL | DIFÍCIL");
				dificultat = teclado.nextLine();
			}
		}
		if (puntos <= 0) {
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

			if(puntos <= 0) {

			}else{
				System.out.println("Distribuïu els " + (puntos-newVida) + " PUNTS");

				System.out.print("ATAC 1 + ");
				newAtac = teclado.nextInt();

				System.out.println("ATAC: " + (atac + newAtac));

				if (puntos <= 0) {

				}else {
					System.out.println("Distribuïu els " + (puntos-newVida-newAtac) + " PUNTS");


					System.out.print("AGILITAT 4 + ");
					newAgilitat = teclado.nextInt();

					System.out.println("AGILITAT: " + (agilitat + newAgilitat));

					if (puntos <= 0) {

					}else {
						System.out.println("Distribuïu els " + (puntos-newVida-newAtac-newAgilitat) + " PUNTS");


						System.out.print("FORSA 4 + ");
						newForsa = teclado.nextInt();

						System.out.println("FORSA: " + (forsa + newForsa));

					}
				}

			}



			asignarPuntos(newVida, newAtac, newAgilitat, newForsa);

		}




		//Personatge player1 = Personatge(nom, vida, atac, agilitat, forsa);

	}

	public static void asignarPuntos(int newVida, int newAtac, int newAgilitat, int newForsa) {
		int puntos = 12;

		if (puntos >= (newVida+newAtac+newAgilitat+newForsa)) {
			System.out.println("NOUS VALORS ASIGNATS");
			int vida = MathUtils.ajustarRang(0, 15, newVida);
			int atac = MathUtils.ajustarRang(0, 3, newAtac);
			int agilitat = MathUtils.ajustarRang(0, 7, newAgilitat);
			int forsa = MathUtils.ajustarRang(0, 7, newForsa);

		}else {
			System.out.println("Els punts asignats es superior als punts que pots aplicar");
		}







	}



}
