package com.evalutel.primval_desktop.Database;


import com.evalutel.primval_desktop.Profil;
import com.evalutel.primval_desktop.Ex.User;

import static java.lang.String.valueOf;

public class MyDataBase
{

    public DataBase database;

    int maxNotePageForIdProfil = 0;
    int maxDureePageForIdProfil = 0;
    int totalDureePageForIdProfil = 0;


    public MyDataBase(DatabaseDesktop database)
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
//    public void insertResultat(long duration, long dateResultat, String chapitre, String onglet, String page, String intitule, String points_obtenus, String points_possibles, String points_max)
    {
        long date = resultat.getDateResultat();
        long duree = resultat.getDuree();
        int chapitre = resultat.getChapitre();
        int onglet = resultat.getOnglet();
        int page = resultat.getPage();
        String intitule = resultat.getIntitule();
        int pointsObtenus = resultat.getPointsObtenus();
        int pointsPossibles = resultat.getPointsPossibles();
        int pointsMaxi = resultat.getPointsMaxi();
        int idProfil = resultat.getIdProfil();


        String intituleNew = intitule.replace("'", "''");
        String sqlRequest = "INSERT INTO RESULTAT " +
                "(duree, date_resultat, chapitre, onglet, page, intitule, points_obtenus, points_possibles, points_max) " +
                "VALUES (" + duree + "," + date + "," + valueOf(chapitre) + "," + valueOf(onglet) + "," + valueOf(page) + "," + "'" + intituleNew + "'" + "," + "'" + valueOf(pointsObtenus) + "'" + "," + "'" + valueOf(pointsPossibles) + "'" + "," + "'" + valueOf(pointsMaxi) + "')";


        database.execute(sqlRequest);


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
