package com.evalutel.primval_desktop.Database;

public class UneNote
{

    private int mPointsObtenus;
    private int mPointsPossibles;
    private long mDuree;

    public UneNote(int pointsObtenus, int pointsPossibles, long duree)
    {
        mPointsObtenus = pointsObtenus;
        mPointsPossibles = pointsPossibles;
        mDuree = duree;
    }

    public void setPointsObtenus(int pO)
    {
        mPointsObtenus = pO;
    }

    public void setPointsPossible(int pP)
    {
        mPointsPossibles = pP;
    }

    public void setDuree(long duree)
    {
        mDuree = duree;
    }

    public int getPointsObt()
    {
        return mPointsObtenus;
    }

    public int getPointsPossible()
    {
        return mPointsPossibles;
    }

    public long getDuree()
    {
        return mDuree;
    }
}
