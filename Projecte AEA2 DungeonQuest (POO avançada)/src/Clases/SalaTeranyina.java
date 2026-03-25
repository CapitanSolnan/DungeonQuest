package Clases;

public class SalaTeranyina extends Sala{

	public SalaTeranyina(Tresor t, Monstre m, boolean explorada) {
		super(t, m, explorada);
	}

	public boolean intentarSortir() {
		if (forsa >= (Math.random() * 12) + 1) {
			return true;
		}else {
			return false;
		}
	}
}
