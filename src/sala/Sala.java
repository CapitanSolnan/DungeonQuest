package sala;

import model.Monstre;
import model.Tresor;

public abstract class Sala {

	private Tresor tresor;
	private Monstre monstre;
	private boolean explorada;

	public Sala(Tresor tresor, Monstre monstre, boolean explorada) {
		super();
		this.tresor = tresor;
		this.monstre = monstre;
		this.explorada = explorada;
	}

	public abstract boolean intentarSortir();

	public boolean estaExplorada() {
		return explorada;
	}

	public String toString() {
		return tresor + " " + monstre + " " + explorada;
	}

}
