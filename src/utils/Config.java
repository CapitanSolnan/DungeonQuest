package utils;

public class Config {
  public enum Dificultats {
    FACIL, NORMAL, DIFICIL;
  }

  public enum Atributs {
    VIDA("Vida"),
    ATAC("Atac"),
    AGILITAT("Agilitat"),
    FORSA("Força");

    private final String nom;

    Atributs(String nom) {
      this.nom = nom;
    }

    public String getNom() {
      return nom;
    }
  }
}
