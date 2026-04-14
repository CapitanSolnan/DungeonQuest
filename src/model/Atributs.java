package model;

public enum Atributs {
  VIDA("Vida", 'v'),
  ATAC("Atac", 'a'),
  AGILITAT("Agilitat", 'g'),
  FORSA("Força", 'f');

  private final String nom;
  private final char lletra;

  Atributs(String nom, char lletra) {
    this.nom = nom;
    this.lletra = lletra;
  }

  public String getNom() {
    return nom;
  }

  public static Atributs desdeLletra(char lletra) {
    char lletraLower = Character.toLowerCase(lletra);
    for (Atributs a : values()) {
      if (a.lletra == lletraLower)
        return a;
    }
    return null;
  }
}