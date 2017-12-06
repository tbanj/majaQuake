package com.example.android.quakereport;

/**
  Engr. Temitope
 *{@link quakes} represernts a vocabulary quakes that the user wants to learn.
 *It contains a default translation and a Miwok translation for that quakes.
 *
 */


public class quakes {
    /**Default Miwok Translation quakes */
    /**This is also known as state when a variable is declare */
    private double mQuakeFelt;



    /**Default Miwok Translation quakes */
    /**This is also known as state when a variable is declare */
    private String mQuakeCity;


    private Long mtimeInMilliseconds;



    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the location where the earthquake happened
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     */


    /**This is also known as state when a variable is declare */
    public quakes(double quakefelt, String quakeCity, Long timeInMilliseconds){
        mQuakeFelt = quakefelt;
        mQuakeCity= quakeCity;
        mtimeInMilliseconds =timeInMilliseconds;


    }







    /**
     * Get the default translation of the quakes.
     */
    public double getQuakeFelt() {
        return mQuakeFelt;
    }

    /**
     * Get the Miwok translation of the quakes.
     */
    public String getQuakeCity() {
        return mQuakeCity;
    }





    public Long getTimeInMilliseconds() {
        return mtimeInMilliseconds;
    }



}
