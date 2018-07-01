package com.chandlerobaker.alcchallenge.android.journalapp.utilities;


import java.util.Date;

import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.SIMPLE_DATE_FORMAT;

public class Utils {

    private static final int LIMIT_NUMBER_CHARACTER = 120;


    public static String contractString(String word)
    {
        if(word == null)
        {
            return null;
        }
        else
        {
            int longueur = word.length();
            if(longueur <= LIMIT_NUMBER_CHARACTER) return  word;
            else
            {
                return word.substring(0, LIMIT_NUMBER_CHARACTER-4) +"...";
            }

        }

    }

    public static String formatDate(Date date)
    {
        String todayS = SIMPLE_DATE_FORMAT.format(new Date());
        String argumentS = SIMPLE_DATE_FORMAT.format(date);

        if(date != null)
        {
            //Diefferent year
            if(!todayS.substring(0,4).equals(argumentS.substring(0,4))) {
                String day = argumentS.substring(8,10);
                String month = argumentS.substring(5,7);
                day = day + "/" + month + "/" + argumentS.substring(0,4);
                return day;
            }
            else
            {
                //Same year
                if(todayS.substring(5,7).contains(argumentS.substring(5,7)))
                {
                    //Same month
                    int a =  Integer.parseInt(todayS.substring(8,10)) - Integer.parseInt(argumentS.substring(8,10));
                    if(a == 0)
                        //hour minute
                        return todayS.substring(11, 16);

                    else if(a >= -6 && a <=-1)
                        return (-1*a) +"d, " + date.toString().substring(11, 16);

                }

            }
            return argumentS.toString().substring(0, 10).replace('-','/');
        }
        else return "";
    }
}
