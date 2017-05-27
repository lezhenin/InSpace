package ru.spbstu.icc.kspt.inspace.androidapp;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class OverviewFragment extends Fragment {

    private JSONObject planet;

    private ImageView planetImage;
    private ImageView buildingsImage;
    private ImageView researchImage;
    private ImageView missionsImage;

    private TextView planetName;
    private TextView metalCount;
    private TextView crystalsCount;
    private TextView deuteriumCount;
    private TextView energyCount;
    private TextView powerCount;
    private TextView fieldsCount;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        planetImage = (ImageView) view.findViewById(R.id.oPlanetImage);
        buildingsImage = (ImageView) view.findViewById(R.id.oBuildings);
        researchImage = (ImageView) view.findViewById(R.id.oResearch);
        missionsImage = (ImageView) view.findViewById(R.id.oMissions);

        planetName = (TextView) view.findViewById(R.id.oPlanetName);
        metalCount = (TextView) view.findViewById(R.id.oMetal);
        crystalsCount = (TextView) view.findViewById(R.id.oCrystals);
        deuteriumCount = (TextView) view.findViewById(R.id.oDeuterium);
        energyCount = (TextView) view.findViewById(R.id.oEnergy);
        powerCount = (TextView) view.findViewById(R.id.oPower);
        fieldsCount = (TextView) view.findViewById(R.id.oFields);

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                planet = new JSONObject(bundle.getString("planet"));
                fillOverviewLayout(planet);

            } else {
                Log.e("Overview Fragment", "Argument is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void fillOverviewLayout(JSONObject planet) throws JSONException {
        JSONObject resources = planet.getJSONObject("resources");

        planetName.setText(planet.getString("name"));
        metalCount.setText(resources.getString("metal"));
        crystalsCount.setText(resources.getString("crystals"));
        deuteriumCount.setText(resources.getString("deuterium"));
        energyCount.setText(planet.getString("energyLevel"));
        powerCount.setText((planet.getDouble("power") * 100) + "%");
        fieldsCount.setText(planet.getString("numberOfEmptyFields") + "/"
                + planet.getString("numberOfFields"));
        if (planet.isNull("currentBuildingUpgrade")) {
            buildingsImage.setColorFilter(Color.GRAY);
        }
        if (planet.isNull("currentResearchUpgrade")) {
            researchImage.setColorFilter(Color.GRAY);
        }
        if (planet.getJSONArray("missions").length() == 0) {
            missionsImage.setColorFilter(Color.GRAY);
        }

        int planetNumber = planet.getJSONObject("position").getInt("numberOfPlanet");
        if(planetNumber < 5) {
            planetImage.setImageDrawable(getResources().getDrawable(R.drawable.planet_hot));
        } else if (planetNumber < 10) {
            planetImage.setImageDrawable(getResources().getDrawable(R.drawable.planet));
        } else  {
            planetImage.setImageDrawable(getResources().getDrawable(R.drawable.planet_cold));
        }
    }

}
