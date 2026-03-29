package combat;

public interface Combatent {
	int getVida();

	int calcularAtac();

	default void rebreDany(int quantitat) {
		int vida = getVida();
		int nouVida = vida - quantitat;
		vida = (nouVida > 0) ? nouVida : 0;
	};

	default boolean estaViu() {
		return getVida() > 0;
	}
}