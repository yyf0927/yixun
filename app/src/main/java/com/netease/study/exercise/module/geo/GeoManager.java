package com.netease.study.exercise.module.geo;

import android.content.Context;
import android.content.SharedPreferences;

public final class GeoManager {
    private static GeoManager instance;
    private final Context context;
    private SharedPreferences mSp;
    private double geoLat;

    //
    // geo
    //
    private double geoLng;
    private boolean hasGeo;

    private GeoManager(Context context) {
        this.context = context;

        load();
    }

    public synchronized static GeoManager getInstance(Context context) {
        if (instance == null) {
            instance = new GeoManager(context);
        }

        return instance;
    }

    public final void update(double geoLat, double geoLng) {
        this.hasGeo = true;
        this.geoLat = geoLat;
        this.geoLng = geoLng;

        save();
    }

    public final boolean hasGeo() {
        // disable geo
        return false;
//        return hasGeo;
    }

    public final double getGeoLat() {
        return geoLat;
    }

    public final double getGeoLng() {
        return geoLng;
    }

    //
    // geo shared preferences
    //

    private void load() {
        SharedPreferences sp = context.getSharedPreferences("geo_manager", Context.MODE_PRIVATE);
        hasGeo = sp.getBoolean("geo", false);
        geoLat = sp.getFloat("geoLat", 0.0f);
        geoLng = sp.getFloat("geoLng", 0.0f);
    }

    private void save() {
        SharedPreferences sp = context.getSharedPreferences("geo_manager", Context.MODE_PRIVATE);
        sp.edit().putFloat("geoLat", (float) geoLat)
                .putFloat("geoLng", (float) geoLng)
                .putBoolean("geo", true)
                .apply();
    }
}
