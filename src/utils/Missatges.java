package utils;

import java.util.Scanner;

public class Missatges {
  public static void imprimirMissatgeBenvinguda(Scanner teclado) {
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
}
