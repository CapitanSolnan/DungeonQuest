package Clases;

public class SalaPont extends Sala {
	private Personatge p;

	public SalaPont(Tresor t, Monstre m, boolean explorada, Personatge p) {
		super(t, m, explorada);
		this.p = p;
	}

	public boolean intentarSortir() {
		if (p.agilitat >= (int)(Math.random() * 12) + 1) {
			return true;
		}else {
			p.vida -=1;
			return false;
		}
	}

}
