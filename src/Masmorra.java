import model.Monstre;
import model.Personatge;
import model.Tresor;
import sala.Sala;
import sala.SalaComuna;
import sala.SalaPont;
import sala.SalaTeranyina;

public class Masmorra {
	// Matriu sala
	private int x;
	private int y;
	Sala[][] mapa = new Sala[x][y];
	private int salesExplorades = 0;
	private int salesTotals;

	// Personatge
	Personatge personatge;

	// Tresors posibles
	private static final Tresor[] LLISTA_TRESORS = {
			new Tresor("Copa d'Or", 100, 2.5),
			new Tresor("Anell de Plata", 50, 0.5),
			new Tresor("Diamant Brut", 500, 1.0)
	};

	// Monstres posibles
	private static final Monstre[] LLISTA_MONSTRES = {
			new Monstre("Zombie", 10, 2),
			new Monstre("Aranya", 5, 1),
			new Monstre("Esquelet", 8, 3)
	};

	public Masmorra(int x, int y, Personatge personatge) {
		this.x = x;
		this.y = y;
		this.mapa = generarMasmorra();
		this.salesTotals = this.x * this.y;
		this.personatge = personatge;
	}

	private Tresor generarTresor() {
		if (Math.random() < 0.30) {
			Tresor plantillaTresor = LLISTA_TRESORS[(int) (Math.random() * LLISTA_TRESORS.length)];

			return new Tresor(plantillaTresor.getNom(), plantillaTresor.getValor(), plantillaTresor.getPes());
		}
		return null;
	}

	private Monstre generarMonstre() {
		if (Math.random() < 0.40) {
			Monstre plantillaMonstre = LLISTA_MONSTRES[(int) (Math.random() * LLISTA_MONSTRES.length)];

			return new Monstre(plantillaMonstre.getNom(), plantillaMonstre.getVida(), plantillaMonstre.getPenalitzacio());
		}
		return null;
	}

	public Sala[][] generarMasmorra() {
		Sala[][] nouMapa = new Sala[this.x][this.y];
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				double r = Math.random();

				// 60 sala comuna
				// 20 sala pont
				// 20 sala teranyina
				if (r < 0.60) {
					nouMapa[i][j] = new SalaComuna(generarTresor(), generarMonstre(), false);
				} else if (r < 0.80) {
					nouMapa[i][j] = new SalaPont(generarTresor(), generarMonstre(), false, personatge);
				} else {
					nouMapa[i][j] = new SalaTeranyina(generarTresor(), generarMonstre(), false, personatge);
				}
			}
		}
		return nouMapa;
	}

	public void mostrarMasmorra(Personatge personatge) {
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				int[] posicioPersonatge = personatge.getPosicio();
				if (posicioPersonatge[0] == i && posicioPersonatge[1] == j) {
					System.out.print("[ & ]");
				} else if (mapa[i][j].estaExplorada()) {
					System.out.print("[ * ]");
				} else {
					System.out.print("[ - ]");
				}
			}
			System.out.println();
		}
	}

	public boolean estaForaMapa(int x, int y) {
		return x < 0 || x >= this.x
				|| y < 0 || y >= this.y;
	}

	public void sumarSalesExplorades() {
		this.salesExplorades++;
	}

	public int getPercentatgeSalesExplorades() {
		return (int) ((this.salesExplorades * 100) / this.salesTotals);
	}

	@Override
	public String toString() {
		return "Masmorra [" + x + "x" + y + "] " + salesExplorades + "/" + salesTotals;
	}
}
