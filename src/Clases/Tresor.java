package Clases;

public class Tresor {
	private String nom;
	private int valor;
	private double pes;

	/**
	 * Constructor de la classe Tresor
	 * 
	 * @param nom   Nom del tresor
	 * @param valor Valor del tresor
	 * @param pes   Pes del tresor
	 */
	public Tresor(String nom, int valor, double pes) {
		super();
		this.nom = nom;
		this.valor = valor;
		this.pes = pes;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " | Valor: " + valor;
	}
}
