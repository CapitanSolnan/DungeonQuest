package utils;

public class ConsoleUtils {
	public static void saltarPagina() {
		// for (int i = 0; i < 50; i++) {
		// System.out.println();
		// }
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void saltarPagina(String titol) {
		// for (int i = 0; i < 50; i++) {
		// System.out.println();
		// }
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println(titol);
	}

}
