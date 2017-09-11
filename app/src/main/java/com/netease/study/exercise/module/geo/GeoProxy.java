package com.netease.study.exercise.module.geo;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class GeoProxy implements AMapLocationListener {
    private final Activity activity;
    private LocationManagerProxy proxy;

    public GeoProxy(Activity activity) {
        this.activity = activity;
    }

    public final void request() {
        if (proxy == null) {
            proxy = LocationManagerProxy.getInstance(activity);
        }

        proxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 100, this);
        proxy.setGpsEnable(false);
    }

    public final void stop() {
        if (proxy != null) {
            proxy.removeUpdates(this);
            proxy.destroy();
        }

        proxy = null;
    }

    protected void onGeoResult() {

    }

    @Override
    public final void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            GeoManager.getInstance(activity).update(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        }

        onGeoResult();
    }

    @Override
    public final void onLocationChanged(Location location) {

    }

    @Override
    public final void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public final void onProviderEnabled(String s) {

    }

    @Override
    public final void onProviderDisabled(String s) {

    }
}
