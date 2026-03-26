package Clases;

public class SalaComuna extends Sala{

	public SalaComuna(Tresor t, Monstre m, boolean explorada) {
		super(t, m, explorada);
	}

	public boolean intentarSortir() {
		return true;
	}

}
