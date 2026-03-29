package combat;

public interface Combatent {
	int getVida();

	int calcularAtac();

	void rebreDany(int quantitat);

	default boolean estaViu() {
		return getVida() > 0;
	}
}