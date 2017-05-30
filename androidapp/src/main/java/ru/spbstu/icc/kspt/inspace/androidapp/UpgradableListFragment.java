package ru.spbstu.icc.kspt.inspace.androidapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpgradableListFragment extends Fragment {

    private RecyclerView upgradablesRecylerView;
    private RecyclerView.Adapter upgradablesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgradable_list, container, false);
        upgradablesRecylerView = (RecyclerView) view.findViewById(R.id.upgradables_view);
        upgradablesRecylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        upgradablesRecylerView.setLayoutManager(layoutManager);

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                JSONArray upgradables = new JSONArray(bundle.getString("upgradables"));
                upgradablesAdapter = new UpgradablesAdapter(upgradables);
                upgradablesRecylerView.setAdapter(upgradablesAdapter);

            } else {
                Log.e("Overview Fragment", "Argument is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    public class UpgradablesAdapter extends RecyclerView.Adapter<UpgradablesAdapter.ViewHolder> {

        Map<String, String> buildingTypeTable = new HashMap<>();
        Map<String, String> researchTypeTable = new HashMap<>();

        {
            buildingTypeTable.put("FACTORY", "Factory");
            buildingTypeTable.put("LABORATORY", "Laboratory");
            buildingTypeTable.put("SHIPYARD", "Shipyard");
            buildingTypeTable.put("POWER_STATION", "Power station");
            buildingTypeTable.put("METAL_MINE", "Metal mine");
            buildingTypeTable.put("CRYSTAL_MINE", "Crystal mine");
            buildingTypeTable.put("DEUTERIUM_MINE", "Deuterium mine");

            researchTypeTable.put("ENERGY", "Energy technology");
            researchTypeTable.put("LASER", "Laser technology");

        }

        private JSONArray upgradables;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView type;
            TextView level;
            TextView metalCount;
            TextView crystalsCount;
            TextView deuteriumCount;
            TextView time;
            ImageView image;

            ViewHolder(View v) {
                super(v);
                type = (TextView) v.findViewById(R.id.uName);
                level = (TextView) v.findViewById(R.id.uLevel);
                metalCount = (TextView) v.findViewById(R.id.uMetal);
                crystalsCount = (TextView) v.findViewById(R.id.uCrystals);
                deuteriumCount = (TextView) v.findViewById(R.id.uDeuterium);
                time = (TextView) v.findViewById(R.id.uTime);
                image = (ImageView) v.findViewById(R.id.uImage);
            }
        }

        UpgradablesAdapter(JSONArray objects) {
            this.upgradables = objects;
        }

        @Override
        public UpgradablesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upgradables_list_item, parent, false);

            return new UpgradablesAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(UpgradablesAdapter.ViewHolder holder, int position) {
            try {
                JSONObject upgradable = upgradables.getJSONObject(position);
                Log.d("json", upgradable.toString());

                JSONObject resources = upgradable.getJSONObject("upgradeCost");
                String metal = resources.getString("metal");
                String crystals = resources.getString("crystals");
                String deuterium = resources.getString("deuterium");

                String type = upgradable.getString("type");
                String level = upgradable.getString("level");
                String time = upgradable.getString("upgradeDuration") + "s";

                if (buildingTypeTable.containsKey(type)) {
                    holder.type.setText(buildingTypeTable.get(type));
                    holder.image.setImageDrawable(
                            getResources().getDrawable(R.drawable.buildings));
                } else if (researchTypeTable.containsKey(type)) {
                    holder.type.setText(researchTypeTable.get(type));
                    holder.image.setImageDrawable(
                            getResources().getDrawable(R.drawable.research));
                }

                holder.level.setText("Level: " + level);
                holder.time.setText(time);

                holder.metalCount.setText(metal);
                holder.crystalsCount.setText(crystals);
                holder.deuteriumCount.setText(deuterium);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return upgradables.length();
        }
    }

}
