package com.evalutel.primval_desktop.Database;


import com.evalutel.primval_desktop.Database.DataBase;
import com.evalutel.primval_desktop.Profil;
import com.evalutel.primval_desktop.User;

public class MyDataBase
{

    DataBase database;


    int maxNotePageForIdProfil = 0;
    int maxDureePageForIdProfil = 0;
    int totalDureePageForIdProfil = 0;


    public void MyDataBase(DataBase database)
    {
        this.database = database;

    }

    public void insertProfil(int idprofil, String nom, String prenom, int age, String classe, int avatar, String photoPath)
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }


    public void updateprofil()
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }


    public void insertResultat(UnResultat resultat)
    {
        String date = resultat.getDateResultat();
        long duree = resultat.getDuree();
        int chapitre = resultat.getChapitre();
        int onglet = resultat.getOnglet();
        int page = resultat.getPage();
        String intitule = resultat.getIntitule();
        int pointsObtenus = resultat.getPointsObtenus();
        int pointsPossibles = resultat.getPointsPossibles();
        int pointsMaxi = resultat.getPointsMaxi();
        int idProfil = resultat.getIdProfil();


        database.execute("INSERT INTO RESULTATS VALUES ("+date+"");


    }


    public void updateResultat(int idResultatServer, int localResultat)
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }

    public int getMaxNotePageForIdProfil(Profil idProfil, int chapitre, int onglet, int page)
    {


        return maxNotePageForIdProfil;

    }

    public int getMaxDureePageForIdProfil(User idProfil, int chapitre, int onglet, int page)
    {


        return maxDureePageForIdProfil;
    }

    public float getTotalDureePageForIdProfil(User idProfil, int chapitre, int onglet, int page)
    {

        return totalDureePageForIdProfil;
    }

    public void removeOnglet(int onglet)
    {

    }


}
