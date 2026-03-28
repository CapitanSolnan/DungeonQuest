package sala;

import model.Monstre;
import model.Tresor;

public class SalaComuna extends Sala {

	public SalaComuna(Tresor t, Monstre m, boolean explorada) {
		super(t, m, explorada);
	}

	public boolean intentarSortir() {
		return true;
	}

}
