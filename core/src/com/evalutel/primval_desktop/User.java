package com.evalutel.primval_desktop;

import de.bitbrain.jpersis.annotations.PrimaryKey;

public class User
{

    @PrimaryKey(true)
    private int idProfil;

    Profil profil;

    private String name;
    private float duration;
    private int notes;

    String Mnemo;
    String intitule;
    int chapitre;
    int onglet;
    int page;
    int pointObtenus;
    int pointPossibles;
    int pointMax;
    String dateResultat;
//    int idLocalSQLite;

    public Profil getProfil ()
    {
        return profil;
    }


    public void setProfil(int idProfil)
    {
        this.idProfil = idProfil;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public float getDuration()
    {
        return duration;
    }

    public void setDuration(float duration)
    {
        this.duration = duration;
    }

    public int getNotes()
    {
        return notes;
    }

    public void setNotes(int notes)
    {
        this.notes = notes;
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    public int getChapitre()
    {
        return chapitre;
    }

    public void setChapitre(int chapitre)
    {
        this.chapitre = chapitre;
    }

    public int getOnglet()
    {
        return onglet;
    }

    public void setOnglet(int onglet)
    {
        this.onglet = onglet;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getPointObtenus()
    {
        return pointObtenus;
    }

    public void setPointObtenus(int pointObtenus)
    {
        this.pointObtenus = pointObtenus;
    }

    public int getPointPossibles()
    {
        return pointPossibles;
    }

    public void setPointPossibles(int pointPossibles)
    {
        this.pointPossibles = pointPossibles;
    }

    public int getPointMax()
    {
        return pointMax;
    }

    public void setPointMax(int pointMax)
    {
        this.pointMax = pointMax;
    }

    public String getDateResultat()
    {
        return dateResultat;
    }

    public void setDateResultat(String dateResultat)
    {
        this.dateResultat = dateResultat;
    }

}