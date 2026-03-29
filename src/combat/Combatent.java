package combat;

public interface Combatent {
	int getVida();

	void setVida(int vida);

	int calcularAtac();

	default void rebreDany(int quantitat) {
		int nouVida = getVida() - quantitat;
		setVida(Math.max(0, nouVida));
	};

	default boolean estaViu() {
		return getVida() > 0;
	}
}