package Clases;

import java.util.Scanner;

public class Mazmorra {
	private Scanner teclado;

	private String opcio;
	private boolean finalitzar;
	
	public Mazmorra(String opcio, boolean finalitzar) {
		super();
		this.teclado = new Scanner(System.in);
		this.opcio = opcio;
		this.finalitzar = finalitzar;
	}


	public void opcions() {
		System.out.println("Que vols fer Explorar (E) / Atacar (A) / Moure (M)");
		 opcio = teclado.nextLine();
		if (opcio.equalsIgnoreCase("e") || opcio.equalsIgnoreCase("explorar")) {
			
		}else if (opcio.equalsIgnoreCase("a") || opcio.equalsIgnoreCase("atacar")) {
			
		}else if (opcio.equalsIgnoreCase("m") || opcio.equalsIgnoreCase("moure")) {
			
		}else {
			System.out.println("Opció no vàlida");
		}
	}
	
	
	public void finalitzar() {
		if (finalitzar) {
			System.out.println("h");
		}else {
			
		}
	}
}
