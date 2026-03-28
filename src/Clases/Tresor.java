package Clases;

public class Tresor {
	private String nom;
	private int valor;
	private double pes;
	public Tresor(String nom, int valor, double pes) {
		super();
		this.nom = nom;
		this.valor = valor;
		this.pes = pes;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nom: " + nom + " | Valor: " + valor;
	}
	
	
}
