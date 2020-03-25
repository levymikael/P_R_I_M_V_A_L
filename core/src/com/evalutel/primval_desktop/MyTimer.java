package com.evalutel.primval_desktop;

import java.util.TimerTask;

public class MyTimer
{
    public java.util.Timer timer = new java.util.Timer();

    public void schedule(TaskEtape etape, long afterDelay)
    {
        timer.schedule(etape, afterDelay);
    }


    public void cancel()
    {
        timer.cancel();
    }


    public static class TaskEtape extends TimerTask
    {
        protected long durationMillis;

        public TaskEtape()
        {

        }

        public TaskEtape(long dT)
        {
            durationMillis = dT;
        }

        @Override
        public void run()
        {

        }
    }

}
