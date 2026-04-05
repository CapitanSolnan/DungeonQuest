package sala;

import model.Monstre;
import model.Personatge;
import model.Tresor;

public class SalaTeranyina extends Sala {
	private Personatge personatge;

	public SalaTeranyina(Tresor tresor, Monstre monstre, boolean explorada, Personatge personatge) {
		super(tresor, monstre, explorada);
		this.personatge = personatge;
	}

	public boolean intentarSortir() {
		return personatge.ferTiradaForsa();
	}
}
