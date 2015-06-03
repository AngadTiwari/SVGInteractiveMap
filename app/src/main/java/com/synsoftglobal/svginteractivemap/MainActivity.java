package com.synsoftglobal.svginteractivemap;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.core.data.SVGPicture;
import com.jiahuan.svgmapview.core.helper.map.SVG;
import com.jiahuan.svgmapview.core.helper.map.SVGBuilder;
import com.jiahuan.svgmapview.core.helper.map.SVGParser;
import com.jiahuan.svgmapview.overlay.SVGMapLocationOverlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class MainActivity extends Activity {

    private SVGMapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (SVGMapView) findViewById(R.id.mapView);

        // load svg string
        try
        {
            mapView.loadMap(convertStreamToString(getAssets().open("map.svg")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Random random =new Random();
        mapView.getController().sparkAtPoint(new PointF(random.nextInt(1000), random.nextInt(1000)), 100, Color.parseColor("#b4e444"), 10);
        SVGMapLocationOverlay locationOverlay = new SVGMapLocationOverlay(mapView);
        locationOverlay.setIndicatorArrowBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        locationOverlay.setPosition(new PointF(400, 500));
        locationOverlay.setIndicatorCircleRotateDegree(90);
        locationOverlay.setMode(SVGMapLocationOverlay.MODE_COMPASS);
        locationOverlay.setIndicatorArrowRotateDegree(-45);
        mapView.getOverLays().add(locationOverlay);
        mapView.refresh();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }
}
