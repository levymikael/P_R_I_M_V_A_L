package com.evalutel.primval_desktop.Database;


public class UnResultat
{
    private int idProfil;
    private int idResultatLocal;
    private int idResultatServer;
    private long mIdEleve = 0;
    private long mDuree = 0;
    private String mMnemo;
    private int mChapitre;
    private int mOnglet;
    private int mPage;
    private String mIntitule;
    private int mPointsObtenus = 0;
    private int mPointsPossibles = 0;
    private int mPointsMaxi;
    private boolean estResultatInsere = false;
    private long dateResultat;

    public UnResultat(String mnemo, int chapitre, int onglet, int page, String intitule, int nbPointsMax, long duree, long dateResultat, int mPointsObtenus, int idResultatLocal, int idResultatServer, int idProfil)
    {
        this.idProfil = idProfil;
        this.dateResultat = dateResultat;
        this.mDuree = duree;
        mMnemo = mnemo;
        mChapitre = chapitre;
        mOnglet = onglet;
        mPage = page;
        mIntitule = intitule;
        mPointsMaxi = nbPointsMax;
        this.idResultatServer = idResultatServer;
        this.idResultatLocal = idResultatLocal;
        this.mPointsObtenus = mPointsObtenus;
    }

    public int getIdProfil()
    {
        return idProfil;
    }

    public int getIdResultatServer()
    {
        return idResultatServer;
    }

    public int getIdResultatLocal()
    {
        return idResultatLocal;
    }

    public long getIdeleve()
    {
        return mIdEleve;
    }

    public long getDateResultat()
    {
        return dateResultat;
    }

    public long getDuree()
    {
        return mDuree;
    }

    public String getMnemo()
    {
        return mMnemo;
    }

    public int getChapitre()
    {
        return mChapitre;
    }

    public int getOnglet()
    {
        return mOnglet;
    }

    public int getPage()
    {
        return mPage;
    }

    public String getIntitule()
    {
        return mIntitule;
    }

    public int getPointsObtenus()
    {
        return mPointsObtenus;
    }

    public void setmPointsObtenus(int mPointsObtenus)
    {
        this.mPointsObtenus = mPointsObtenus;
    }

    public void setmPointsPossibles(int mPointsPossibles)
    {
        this.mPointsPossibles = mPointsPossibles;
    }

    public int getPointsPossibles()
    {
        return mPointsPossibles;
    }

    public int getPointsMaxi()
    {
        return mPointsMaxi;
    }

    public boolean estInsere()
    {
        return estResultatInsere;
    }

    public void setIdeleve(long idE)
    {
        mIdEleve = idE;
    }

    public void setInsere()
    {
        estResultatInsere = true;
    }

    public void setDate(long date)
    {
        dateResultat = date;
    }

    public void setDuree(long duree)
    {
        mDuree = duree;
    }

    public void setNoteWithTampon(int pointsObtenus, int pointsPossible)
    {
        mPointsObtenus = pointsObtenus;
        mPointsPossibles = pointsPossible;

//        TamponNote.saveTamponNote(this, context);
    }

    public void setNoteWithOutTampon(int pointsObtenus, int pointsPossible)
    {
        mPointsObtenus = pointsObtenus;
        mPointsPossibles = pointsPossible;
    }


}






