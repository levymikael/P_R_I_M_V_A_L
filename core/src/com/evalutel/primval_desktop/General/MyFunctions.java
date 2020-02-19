//package com.evalutel.primval.aaGeneral;
//
////import android.app.Activity;
////import android.app.AlertDialog;
////import android.content.Context;
////import android.content.DialogInterface;
////import android.content.SharedPreferences;
////import android.content.res.Resources;
////import android.graphics.Bitmap;
////import android.graphics.BitmapFactory;
////import android.graphics.Canvas;
////import android.graphics.RectF;
////import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
////import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
////import android.util.Log;
////import android.widget.ImageView;
////
////import com.evalutel.primval.R;
////
////import org.apache.http.HttpResponse;
////import org.apache.http.HttpStatus;
////import org.apache.http.StatusLine;
////import org.apache.http.client.ClientProtocolException;
////import org.apache.http.client.CookieStore;
////import org.apache.http.client.methods.HttpGet;
////import org.apache.http.client.protocol.ClientContext;
////import org.apache.http.cookie.Cookie;
////import org.apache.http.impl.client.BasicCookieStore;
////import org.apache.http.impl.client.DefaultHttpClient;
////import org.apache.http.protocol.BasicHttpContext;
////import org.apache.http.protocol.HttpContext;
//
//import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.net.URI;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.naming.Context;
//
//public class MyFunctions
//{
//
//    public static Date getDateSouscriptionStart(Context context)
//    {
//        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("SHARED_PREFENRENCE", Context.MODE_PRIVATE);
//        String dateSouscription = sharedPref.getString(context.getApplicationContext().getString(R.string.date_souscription), "");
//
//
//        return MyFunctions.convertStringToDate(dateSouscription, "yyyy-MM-dd");
//    }
//
//    public static Date getDateSouscriptionEnd(Context context)
//    {
//        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("SHARED_PREFENRENCE", Context.MODE_PRIVATE);
//        String datefin = sharedPref.getString(context.getApplicationContext().getString(R.string.date_fin), "");
//
//
//        return MyFunctions.convertStringToDate(datefin, "yyyy-MM-dd");
//    }
//
//    public static void alert(Context context, String message)
//    {
//        AlertDialog.Builder bld = new AlertDialog.Builder(context);
//        bld.setMessage(message);
//        bld.setNeutralButton("OK", null);
//        bld.create().show();
//    }
//
//    public static void setOrUpdatePurchaseTest(Context context)
//    {
//        Date startDate;
//        Date endDate;
//
//
//        if (MyFunctions.isAbonnementValide(context))
//        {
//            startDate = MyFunctions.getDateSouscriptionStart(context);
//            Date dateFin = MyFunctions.getDateSouscriptionEnd(context);
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(dateFin);
//            cal.add(Calendar.DATE, 7);
//
//            endDate = cal.getTime();
//        }
//        else
//        {
//            startDate = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(startDate);
//            cal.add(Calendar.DATE, 7);
//
//            endDate = cal.getTime();
//        }
//
//        String dateSouscriptionString = MyFunctions.convertDateToString(startDate);
//        String dateFinSouscriptionString = MyFunctions.convertDateToString(endDate);
//
//
//        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("SHARED_PREFENRENCE", Context.MODE_PRIVATE);//((Activity)context).getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(((Activity) context).getString(R.string.date_souscription), dateSouscriptionString);
//        editor.putString(((Activity) context).getString(R.string.date_fin), dateFinSouscriptionString);
//        editor.putBoolean(((Activity) context).getString(R.string.is_test_consume), true);
//        editor.commit();
//    }
//
//    public static void setOrUpdateLocalPurchase(Context context, int numberMonths)
//    {
//        Date startDate;
//        Date endDate;
//
//
//        if (MyFunctions.isAbonnementValide(context))
//        {
//            startDate = MyFunctions.getDateSouscriptionStart(context);
//            Date dateFin = MyFunctions.getDateSouscriptionEnd(context);
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(dateFin);
//            cal.add(Calendar.MONTH, numberMonths);
//
//            endDate = cal.getTime();
//        }
//        else
//        {
//            startDate = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(startDate);
//            cal.add(Calendar.MONTH, numberMonths);
//
//            endDate = cal.getTime();
//        }
//
//        String dateSouscriptionString = MyFunctions.convertDateToString(startDate);
//        String dateFinSouscriptionString = MyFunctions.convertDateToString(endDate);
//
//
//        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("SHARED_PREFENRENCE", Context.MODE_PRIVATE);//((Activity)context).getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(((Activity) context).getString(R.string.date_souscription), dateSouscriptionString);
//        editor.putString(((Activity) context).getString(R.string.date_fin), dateFinSouscriptionString);
//        editor.commit();
//    }
//
//    public static boolean isAbonnementValide(Context context)
//    {
//        boolean retour = false;
//
//        Date date = new Date();
//
//        Date dateDebut = MyFunctions.getDateSouscriptionStart(context);
//        Date dateFin = MyFunctions.getDateSouscriptionEnd(context);
//
//
//        int ok = 5;
//        ok++;
//
//        if (dateFin != null)
//        {
//            if (date.compareTo(dateFin) < 0)
//            {
//                retour = true;
//
//            }
//        }
//
//
//        return retour;
//    }
//
//    public static void setPic(Context context, String path, ImageView mImageView, int largeurImageView, int hauteurImageView)
//    {
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, bmOptions);
//
//
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = 1;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
//
//        Bitmap croppedBmp = MyFunctions.scaleCenterCrop(bitmap, largeurImageView, hauteurImageView);
//
//        Resources res = context.getResources();
//        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, croppedBmp);
//        dr.setCornerRadius(largeurImageView / 2);
//        mImageView.setImageDrawable(dr);
//
//        //mImageView.setImageBitmap(croppedBmp);
//    }
//
//    public static Bitmap scaleCenterCrop(FreeType.Bitmap source, int newWidth, int newHeight)
//    {
//        int sourceWidth = source.getWidth();
//        int sourceHeight = source.getHeight();
//
//        // Compute the scaling factors to fit the new height and width, respectively.
//        // To cover the final image, the final scaling will be the bigger
//        // of these two.
//        float xScale = (float) newWidth / sourceWidth;
//        float yScale = (float) newHeight / sourceHeight;
//        float scale = Math.max(xScale, yScale);
//
//        // Now get the size of the source bitmap when scaled
//        float scaledWidth = scale * sourceWidth;
//        float scaledHeight = scale * sourceHeight;
//
//        // Let's find out the upper left coordinates if the scaled bitmap
//        // should be centered in the new size give by the parameters
//        float left = (newWidth - scaledWidth) / 2;
//        float top = (newHeight - scaledHeight) / 2;
//
//        // The target rectangle for the new, scaled version of the source bitmap will now
//        // be
//        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
//
//        // Finally, we create a new bitmap of the specified size and draw our new,
//        // scaled bitmap onto it.
//        FreeType.Bitmap dest = FreeType.Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
//        Canvas canvas = new Canvas(dest);
//        canvas.drawBitmap(source, null, targetRect, null);
//
//        return dest;
//    }
//
//    public static Date convertStringToDate(String dateString, String formatString)
//    {
//        if (dateString == null)
//        {
//            return null;
//        }
//        Date date = null;
//        SimpleDateFormat format = new SimpleDateFormat(formatString);
//        try
//        {
//            date = format.parse(dateString);
//            System.out.println(date);
//        } catch (ParseException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return date;
//    }
//
//    public static Date convertStringToDate(String dateString)
//    {
//        if (dateString == null)
//        {
//            dateString = "2016-01-01 00:00:00";
//        }
//        Date date = null;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try
//        {
//            date = format.parse(dateString);
//            System.out.println(date);
//        } catch (ParseException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return date;
//    }
//
//    public static String convertDateToString(Date date, String formatString)
//    {
//
//        SimpleDateFormat dateformat = new SimpleDateFormat(formatString);
//        String datetime = dateformat.format(date);
//
//        return datetime;
//    }
//
//
//    public static String convertDateToString(Date date)
//    {
//
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String datetime = dateformat.format(date);
//
//        return datetime;
//    }
//
//    public static String convertDateToString2(Date date)
//    {
//
//        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy");
//        String datetime = dateformat.format(date);
//
//        return datetime;
//    }
//
//    public static boolean containsStringWithPrime(String container, String str1, String str2)
//    {
//        boolean test = container.contains(str1 + "'" + str2) || container.contains(str1 + "′" + str2) || container.contains(str1 + "`" + str2) || container.contains(str1 + "’" + str2) || container.contains(str1 + "‘" + str2);
//        return test;
//    }
//
//    public static boolean equalStringWithPrime(String container, String str1, String str2)
//    {
//        boolean test = container.equals(str1 + "'" + str2) || container.equals(str1 + "′" + str2) || container.equals(str1 + "`" + str2) || container.equals(str1 + "’" + str2) || container.equals(str1 + "‘" + str2);
//        return test;
//    }
//
//    public static int convertDpDoPixels(int dp)
//    {
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
//    }
//
//
//    public static String synchronousRequest(String requestStr)
//    {
//        String retour = null;
//        URI myUri = URI.create(requestStr);
//
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//
//        CookieStore cookieStore = new BasicCookieStore();
//
//        // Create local HTTP context
//        HttpContext localContext = new BasicHttpContext();
//        // Bind custom cookie store to the local context
//        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
//
//        HttpResponse response = null;
//        try
//        {
//            response = httpclient.execute(new HttpGet(myUri), localContext);
//
//
//            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
//            if (cookies.isEmpty())
//            {
//                System.out.println("None");
//            }
//            else
//            {
//                for (int i = 0; i < cookies.size(); i++)
//                {
//                    System.out.println("- " + cookies.get(i).toString());
//                }
//            }
//
//            StatusLine statusLine = response.getStatusLine();
//            if (statusLine.getStatusCode() == HttpStatus.SC_OK)
//            {
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                response.getEntity().writeTo(out);
//                retour = out.toString();
//                Log.d("retour", retour);
//                out.close();
//
//
//            }
//            else
//            {
//                //Closes the connection.
//                response.getEntity().getContent().close();
//                throw new IOException(statusLine.getReasonPhrase());
//            }
//        } catch (ClientProtocolException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return retour;
//
//    }
//
//    /*public static String httpRequest(String url)
//    {
//        String retour = null;
//        try
//        {
//            URL obj = new URL(url);
//
//            try
//            {
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//                // optional default is GET
//                con.setRequestMethod("GET");
//
//                //add request header
//
//
//                int responseCode = con.getResponseCode();
//                System.out.println("\nSending 'GET' request to URL : " + url);
//                System.out.println("Response Code : " + responseCode);
//
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                //print result
//                retour = response.toString();
//
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//
//        }
//        catch (MalformedURLException e)
//        {
//            e.printStackTrace();
//        }
//
//        return retour;
//    }*/
//
//
//    public static void myAlertMessage(Context context, String message, String title)
//    {
//        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
//        dlgAlert.setMessage(message);
//        dlgAlert.setTitle(title);
//        dlgAlert.setPositiveButton("Ok",
//                new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        //dismiss the dialog
//                    }
//                });
//        dlgAlert.setCancelable(true);
//        dlgAlert.create().show();
//    }
//
//    public static void myAlertYesNoMessage(Context context, String message, String title, final Runnable runnableYes)
//    {
//        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
//        dlgAlert.setMessage(message);
//        dlgAlert.setTitle(title);
//        dlgAlert.setPositiveButton("Oui",
//                new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        runnableYes.run();
//                    }
//                });
//
//        dlgAlert.setNegativeButton("Non",
//                new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        //dismiss the dialog
//                    }
//                });
//        dlgAlert.setCancelable(true);
//        dlgAlert.create().show();
//    }
//
//    public static int getResId(String resName, Class<?> c)
//    {
//
//        try
//        {
//            Field idField = c.getDeclaredField(resName);
//            return idField.getInt(idField);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//
//}
