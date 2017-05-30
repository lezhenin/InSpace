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

public class ConstructablesListFragment extends Fragment{
    private RecyclerView constructablesRecylerView;
    private RecyclerView.Adapter constructablesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_constructables_list, container, false);
        constructablesRecylerView = (RecyclerView) view.findViewById(R.id.constructables_view);
        constructablesRecylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        constructablesRecylerView.setLayoutManager(layoutManager);

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                JSONArray constructables = new JSONArray(bundle.getString("constructables"));
                constructablesAdapter = new ConstructablesListFragment.ConstructablesAdapter(constructables);
                constructablesRecylerView.setAdapter(constructablesAdapter);

            } else {
                Log.e("Overview Fragment", "Argument is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    public class ConstructablesAdapter extends
            RecyclerView.Adapter<ConstructablesListFragment.ConstructablesAdapter.ViewHolder> {

        Map<String, String> shipTypeTable = new HashMap<>();
        {
            shipTypeTable.put("FIGHTER", "Fighter");
            shipTypeTable.put("SMALL_CARGO", "Small Cargo");
        }

        private JSONArray constructables;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView type;
            TextView number;
            TextView metalCount;
            TextView crystalsCount;
            TextView deuteriumCount;
            TextView time;
            TextView attack;
            TextView speed;
            TextView structure;
            TextView shield;
            TextView capacity;
            ImageView image;

            ViewHolder(View v) {
                super(v);

                type = (TextView) v.findViewById(R.id.cName);
                number = (TextView) v.findViewById(R.id.cNumber);
                metalCount = (TextView) v.findViewById(R.id.cMetal);
                crystalsCount = (TextView) v.findViewById(R.id.cCrystals);
                deuteriumCount = (TextView) v.findViewById(R.id.cDeuterium);
                time = (TextView) v.findViewById(R.id.cTime);
                attack = (TextView) v.findViewById(R.id.cAttack);
                speed = (TextView) v.findViewById(R.id.cSpeed);
                structure = (TextView) v.findViewById(R.id.cStructure);
                shield = (TextView) v.findViewById(R.id.cShield);
                capacity = (TextView) v.findViewById(R.id.cCapacity);
                image = (ImageView) v.findViewById(R.id.cImage);

            }
        }

        ConstructablesAdapter(JSONArray objects) {
            this.constructables = objects;
        }

        @Override
        public ConstructablesListFragment.ConstructablesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.constructables_list_item, parent, false);

            return new ConstructablesListFragment.ConstructablesAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ConstructablesListFragment.ConstructablesAdapter.ViewHolder holder, int position) {
            try {
                JSONObject constructable = constructables.getJSONObject(position);
                Log.d("json", constructable.toString());

                JSONObject resources = constructable.getJSONObject("constructionCost");
                String metal = resources.getString("metal");
                String crystals = resources.getString("crystals");
                String deuterium = resources.getString("deuterium");

                String type = constructable.getString("type");
                String number = "5";
                String time = constructable.getString("constructionDuration") + "s";

                String attack = constructable.getString("attack");
                String speed = constructable.getString("speed");
                String structure = constructable.getString("structure");
                String shield = constructable.getString("shieldStructure");
                String capacity = constructable.getString("capacity");

                if (shipTypeTable.containsKey(type)) {
                    holder.type.setText(shipTypeTable.get(type));
                    holder.image.setImageDrawable(
                            getResources().getDrawable(R.drawable.fleet));
                }

                holder.number.setText("Number: " + number);
                holder.time.setText(time);

                holder.metalCount.setText(metal);
                holder.crystalsCount.setText(crystals);
                holder.deuteriumCount.setText(deuterium);

                holder.capacity.setText(getString(R.string.capacity) + ": " + capacity);
                holder.attack.setText(getString(R.string.attack) + ": " + attack);
                holder.structure.setText(getString(R.string.structure) + ": " + structure);
                holder.shield.setText(getString(R.string.shield) + ": " + shield);
                holder.speed.setText(getString(R.string.speed) + ": " +speed);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return constructables.length();
        }
    }
}
