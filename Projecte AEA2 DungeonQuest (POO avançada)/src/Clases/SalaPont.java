package Clases;

public class SalaPont extends Sala {
	
	public SalaPont(Tresor t, Monstre m, boolean explorada) {
		super(t, m, explorada);
	}

	public boolean intentarSortir() {
		if (agilitat >= (Math.random() * 12) + 1) {
			return true;
		}else {
			vida -=1;
			return false;
		}
	}
	
}
