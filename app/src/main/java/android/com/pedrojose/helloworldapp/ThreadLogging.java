package android.com.pedrojose.helloworldapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by PedroJosé on 03/01/2016.
 */
public class ThreadLogging implements LocationListener {
    private User u;
    private float carga;
    Location starting = null;
    private String modal;
    private int velocidadeTotal,leituras;
    private int distSubida;
    private int distDescida;
    private int totDistance;
    private RecordMap recordMap;
    LoggingAct loggingAct;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 150; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 15000; // in Milliseconds Talvez Passar para 10 segundos...
    protected LocationManager locationManager;

    public ThreadLogging(User u, float carga, String modal, LoggingAct loggingAct, RecordMap rm) {
        this.u = u;
        this.loggingAct = loggingAct;

        this.carga = carga;
        this.modal = modal;
        this.velocidadeTotal=leituras=distSubida=distDescida=totDistance=0;
        this.recordMap = rm;
        locationManager = (LocationManager) loggingAct.getSystemService(Context.LOCATION_SERVICE);
        PackageManager pm = loggingAct.getPackageManager();

        String provider = locationManager.getBestProvider(createFineCriteria(), false);
        if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {

            if (ActivityCompat.checkSelfPermission(this.loggingAct, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.loggingAct, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(provider, 4500, 1, this); /*E NECESSARIO REVER ESTE PONTO!!! TALVEZ MAIS METROS SENÃO 1 LEITURA/METRO = KILL!!*/
            } else {
                Toast.makeText(this.loggingAct, "Reveja condições de permissão", Toast.LENGTH_SHORT).show();
            }
        }
    }




    public RecordMap getRecordMap() {
        return this.recordMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(starting==null){
          starting=location;
            this.loggingAct.updateLatLonAl(location.getLatitude(),location.getLongitude(),location.getAltitude());
        }
        else{
            velocidadeTotal+=location.getSpeed();
            leituras++;
            float media=(float)velocidadeTotal/leituras;
            if(starting.getAltitude()<location.getAltitude()) distSubida+=starting.distanceTo(location);
            if(starting.getAltitude()>location.getAltitude()) distDescida+=starting.distanceTo(location);
            totDistance+=starting.distanceTo(location);
            recordMap.addRecord(new Record(starting,location,media,location.getSpeed(),distSubida,distDescida,totDistance,loggingAct.getDiffic(),this.u,carga,modal));
            this.loggingAct.updateLatLonAl(location.getLatitude(),location.getLongitude(),location.getAltitude());
            starting=location;
        }
        //location.getSpeed() metros por segundo
        //location.getAltitude() metros
		//distanciaTotalPercorrida

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void removeRequest() {
        if (ActivityCompat.checkSelfPermission(this.loggingAct, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.loggingAct, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }

    }
    public static Criteria createFineCriteria(){
        Criteria c=new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(true);
        c.setBearingRequired(true);
        c.setSpeedRequired(true);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        return c;
    }
}
