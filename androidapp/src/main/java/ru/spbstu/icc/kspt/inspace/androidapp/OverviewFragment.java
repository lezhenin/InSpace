package ru.spbstu.icc.kspt.inspace.androidapp;


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
    private TextView planetName;
    private TextView metalCount;
    private TextView crystalsCount;
    private TextView deuteriumCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        planetImage = (ImageView) view.findViewById(R.id.hPlanetImage);
        planetName = (TextView) view.findViewById(R.id.oPlanetName);
        metalCount = (TextView) view.findViewById(R.id.oMetal);
        crystalsCount = (TextView) view.findViewById(R.id.oCrystals);
        deuteriumCount= (TextView) view.findViewById(R.id.oDeuterium);

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                planet = new JSONObject(bundle.getString("planet"));
                planetName.setText(planet.getString("name"));
                JSONObject resources = planet.getJSONObject("resources");
                metalCount.setText(resources.getString("metal"));
                crystalsCount.setText(resources.getString("crystals"));
                deuteriumCount.setText(resources.getString("deuterium"));
            } else {
                Log.e("Overview Fragment", "Argument is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
