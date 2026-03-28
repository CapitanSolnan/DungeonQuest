package sala;

import model.Monstre;
import model.Personatge;
import model.Tresor;

public class SalaTeranyina extends Sala {
	private Personatge p;

	public SalaTeranyina(Tresor t, Monstre m, boolean explorada, Personatge p) {
		super(t, m, explorada);
		this.p = p;
	}

	public boolean intentarSortir() {
		if (p.forsa >= (int) (Math.random() * 12) + 1) {
			return true;
		} else {
			return false;
		}
	}

	// 20%
}
