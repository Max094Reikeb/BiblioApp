package com.biblio.bibliotheque.Models;

import java.time.LocalDate;

public class Book {
    private String titre;
    private String auteurNom;
    private String auteurPrenom;
    private LocalDate dateParution;
    private int colonne;
    private int rangee;
    private String resume;
    private String imagePath;

    // Constructeur par défaut (nécessaire pour JAXB)


    // Constructeur
    public Book(String titre, String auteurNom, String auteurPrenom, LocalDate dateParution, int colonne, int rangee, String resume, String imagePath) {
        this.titre = titre;
        this.auteurNom = auteurNom;
        this.auteurPrenom = auteurPrenom;
        this.dateParution = dateParution;
        this.colonne = colonne;
        this.rangee = rangee;
        this.resume = resume;
        this.imagePath = imagePath;
    }

    // Getters et Setters (à générer avec IntelliJ : Alt + Insert)
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteurNom() { return auteurNom; }
    public void setAuteurNom(String auteurNom) { this.auteurNom = auteurNom; }

    public String getAuteurPrenom() { return auteurPrenom; }
    public void setAuteurPrenom(String auteurPrenom) { this.auteurPrenom = auteurPrenom; }

    public LocalDate getDateParution() { return dateParution; }
    public void setDateParution(LocalDate dateParution) { this.dateParution = dateParution; }

    public int getColonne() { return colonne; }
    public void setColonne(int colonne) { this.colonne = colonne; }

    public int getRangee() { return rangee; }
    public void setRangee(int rangee) { this.rangee = rangee; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
