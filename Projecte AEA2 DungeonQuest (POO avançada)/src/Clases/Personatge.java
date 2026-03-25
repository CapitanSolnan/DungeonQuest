package Clases;

public class Personatge {

	private String nom;
	protected int vida;
	private int atac;
	private int experiencia;
	protected int agilitat;
	protected int forsa;
	private int[] posicio = new int[2];
	private Tresor equipament;
	
	public Personatge(String nom, int vida, int atac, int experiencia, int agilitat, int forsa, int posicioX, int posicioY,
			Tresor equipament) {
		super();
		this.nom = nom;
		this.vida = comprovarRango(5, 20, vida);
		this.atac = comprovarRango(1, 4, atac);
		this.experiencia = 0;
		this.agilitat = comprovarRango(4, 11, agilitat);
		this.forsa = comprovarRango(4, 11, forsa);
		this.posicio[0] = posicioX;
		this.posicio[1] = posicioY;
		this.equipament = equipament;
	}
	
	public int comprovarRango(int min, int max, int valor){
		if (valor < min || valor > max){
			return '0';
		}
		return valor;
	}
	
	
	public void atacar(Monstre m) {
		
	}
	public void explorar() {
		
	}
	public void moure(char direccio) {
		if (direccio == 'N') {
			posicio[0]+= 1;
			
		}else if (direccio == 'S') {
			posicio[0]-= 1;

		}else if (direccio == 'E') {
			posicio[1]+= 1;

		}else if (direccio == 'O') {
			posicio[1]-= 1;

		}else {
			System.out.println("Direcció erronia");
		}
	}
	
	public void calcularAtac() {
		
	}
	
	
}
