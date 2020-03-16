package com.evalutel.primval_desktop;

public class Profil
{

    private String name;
    private float duration;
    private int notes;


    int idCompte;
    String nom;
    int age;
    String prenom;
    int avatar;
    String classe;
    String photoPath;


    public Profil(int idCompte, String nom, String prenom, int age, String classe, int avatar)
    {

        this.idCompte = idCompte;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.classe = classe;
        this.avatar = avatar;

    }

    public Profil(int idCompte, String nom, String prenom, int age, String classe, String photoPath)
    {
        this.idCompte = idCompte;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.classe = classe;
        this.photoPath = photoPath;
    }


}