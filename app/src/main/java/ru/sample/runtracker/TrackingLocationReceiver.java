package ru.sample.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by ITS on 25.07.2015.
 */
public class TrackingLocationReceiver extends LocationReceiver {

    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }


}
