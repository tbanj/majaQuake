package com.example.android.quakereport;


import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Engr. Temitope on 18/10/2017.
 */

public class QuakeAdapter extends ArrayAdapter<quakes> {
    /*Resource id for background color for the list of words in this category*/
    private double mMagnitude;


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param earthquakes   A List of Word objects to display in a list
     */
    public QuakeAdapter(Activity context, ArrayList<quakes> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        quakes currentforcast = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView feltTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the version name from the current Word object and
        // set this text on the name TextView
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentforcast.getQuakeFelt());
        // Display the magnitude of the current earthquake in that TextView
        feltTextView.setText(formattedMagnitude);

        // Find the TextView in the list_item.xml layout with the ID location_offset
        //to populate the primary location which earthquake happened
        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);

        // Find the TextView in the list_item.xml layout with the ID primary_location to
        //to populate the primary location which earthquake happened
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.primary_location);

        String primaryLocation ="";
        String locationOffset="";
        String wordSubString =currentforcast.getQuakeCity();
        int locationOfIndexWord =wordSubString.indexOf("of");
        try {
            if(locationOfIndexWord !=-1){
                locationOffset= wordSubString.substring(1, locationOfIndexWord+3);
                offsetTextView.setText(locationOffset);
                primaryLocation=wordSubString.substring(locationOfIndexWord +3);
                cityTextView.setText(primaryLocation);
            }

            else{
                locationOffset= "Near the";
                offsetTextView.setText(locationOffset);
                primaryLocation= wordSubString;
                cityTextView.setText(primaryLocation);

            }
        } catch (Exception e) {

            Log.e("wordSubString","There was error during fectching of location of earthquake");
            e.printStackTrace();
        }


        //cityTextView.setText(currentforcast.getQuakeCity());

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentforcast.getTimeInMilliseconds());


        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Get the version number from the current Word object and
        // set this text on the number TextView
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);


        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Get the TIME from the current Word object and
        // set this text on the number TextView
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);


        // Set the proper background color on the magnitude circle.
        // Find the TextView in the list_item.xml layout with the ID magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentforcast.getQuakeFelt());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);






        // Return the whole list item layout (containing 2 TextViews )
        // so that it can be shown in the ListView
        return listItemView;
    }

    private int getMagnitudeColor(double quakeFelt) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(quakeFelt);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);

    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date timeObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(timeObject);
    }


    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

}
