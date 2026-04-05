package sala;

import model.Monstre;
import model.Personatge;
import model.Tresor;

public class SalaPont extends Sala {
	private Personatge personatge;

	public SalaPont(Tresor tresor, Monstre monstre, boolean explorada, Personatge personatge) {
		super(tresor, monstre, explorada);
		this.personatge = personatge;
	}

	public boolean intentarSortir() {
		return personatge.ferTiradaAgilitat();
	}
}
