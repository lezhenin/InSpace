package ru.spbstu.icc.kspt.inspace.androidapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class PlanetViewActivity extends AppCompatActivity {

    private static String PLANET_URL;
    private JSONObject planet;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private OverviewFragment overviewFragment;
    private UpgradableListFragment buildingsFragment;
    private UpgradableListFragment researchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_view);

        PLANET_URL = getIntent().getExtras().getString("planet url");

        overviewFragment = new OverviewFragment();
        buildingsFragment = new UpgradableListFragment();
        researchFragment = new UpgradableListFragment();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.menu_overview:
                        setFragment(overviewFragment);
                        break;
                    case R.id.menu_buildings:
                        setFragment(buildingsFragment);
                        break;
                    case R.id.menu_research:
                        setFragment(researchFragment);
                        break;
                }
                return true;
            }
        });

        new DownloadPlanetTask().execute(getResources().getString(R.string.url) + PLANET_URL);
    }

    private void initDrawer(JSONObject planet) throws JSONException {
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.hPlanetName);
        name.setText(planet.getString("name"));
        ImageView image = (ImageView) header.findViewById(R.id.hPlanetImage);

        int planetNumber = planet.getJSONObject("position").getInt("numberOfPlanet");
        if(planetNumber < 5) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.planet_hot));
        } else if (planetNumber < 10) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.planet));
        } else  {
            image.setImageDrawable(getResources().getDrawable(R.drawable.planet_cold));
        }
    }

    private void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }



    private class DownloadPlanetTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            return restTemplate.getForObject(params[0], String.class);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                planet = new JSONObject(s);
                Log.d("json", planet.toString());
                initDrawer(planet);
                initFragments(planet);
                setFragment(overviewFragment);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFragments(JSONObject planet) throws JSONException {
        Bundle planetBundle = new Bundle();
        planetBundle.putString("planet", planet.toString());
        overviewFragment.setArguments(planetBundle);

        Bundle buildingsBundle = new Bundle();
        buildingsBundle.putString("upgradables", planet.getJSONArray("buildings").toString());
        buildingsFragment.setArguments(buildingsBundle);

        Bundle researchBundle = new Bundle();
        researchBundle.putString("upgradables", planet.getJSONArray("research").toString());
        researchFragment.setArguments(researchBundle);
    }


}
