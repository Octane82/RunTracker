package ru.sample.runtracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

/**
 * Класс синглтон
 * запроса обновлений местоположения в форме широковещательных интентов
 */
public class RunManager {

    private static final String TAG = "RunManager";
    public static final String ACTION_LOCATION = "ru.sample.runtracker.ACTION_LOCATION";
    private static RunManager sRunManager;
    private Context mAppContext;
    private LocationManager mLocationManager;

    // Закрытый конструктор заставляет использовать
    // RunManager.get(Context)
    private RunManager(Context appContext) {
        mAppContext = appContext;
        mLocationManager = (LocationManager)mAppContext
                .getSystemService(Context.LOCATION_SERVICE);
    }

    public static RunManager get(Context c) {
        if (sRunManager == null) {
        // Использование контекста приложения для предотвращения
        // утечки активностей
            sRunManager = new RunManager(c.getApplicationContext());
        }
        return sRunManager;
    }

    // интент будет рассылаться при оновлении местоположения
    private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(mAppContext, 0, broadcast, flags);
    }

    // мы приказываем LocationManager передавать обновленную
    // информацию местоположения через поставщика данных GPS как можно чаще
    public void startLocationUpdates() {
        String provider = LocationManager.GPS_PROVIDER;

        // Получение последнего известного местоположения
        // и его рассылка (если данные доступны).
        Location lastKnown = mLocationManager.getLastKnownLocation(provider);
        if (lastKnown != null) {
        // Время инициализируется текущим значением
            lastKnown.setTime(System.currentTimeMillis());
            broadcastLocation(lastKnown);
        }

        // Запуск обновлений из LocationManager
        PendingIntent pi = getLocationPendingIntent(true);
        // requestLocationUpdates(.., минимальное ожидание(мсек), минимальное смещение (м))
        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
    }

    private void broadcastLocation(Location location) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
        mAppContext.sendBroadcast(broadcast);
    }

    public void stopLocationUpdates() {
        PendingIntent pi = getLocationPendingIntent(false);
        if (pi != null) {
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    public boolean isTrackingRun() {
        return getLocationPendingIntent(false) != null;
    }


}
