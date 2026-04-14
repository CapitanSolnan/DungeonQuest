package core;

public enum Dificultats {
  FACIL("Fàcil"),
  NORMAL("Normal"),
  DIFICIL("Difícil");

  private final String nom;

  Dificultats(String nom) {
    this.nom = nom;
  }

  public String getNom() {
    return nom;
  }
}
