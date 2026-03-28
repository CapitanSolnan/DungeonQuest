package sala;

import model.Monstre;
import model.Tresor;

public abstract class Sala {

	private Tresor t;
	private Monstre m;
	private boolean explorada;

	public Sala(Tresor t, Monstre m, boolean explorada) {
		super();
		this.t = t;
		this.m = m;
		this.explorada = explorada;
	}

	public abstract boolean intentarSortir();

	public String toString() {
		return t + " " + m + " " + explorada;
	}

}
