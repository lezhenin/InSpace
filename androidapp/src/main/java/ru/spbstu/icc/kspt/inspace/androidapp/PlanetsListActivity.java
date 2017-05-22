package ru.spbstu.icc.kspt.inspace.androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class PlanetsListActivity extends AppCompatActivity {

    private static String PLANETS_URL = "/planets/";

    private RecyclerView planetsRecyclerView;
    private RecyclerView.Adapter planetAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planets_list);

        planetsRecyclerView = (RecyclerView) findViewById(R.id.planets_view);
        planetsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        planetsRecyclerView.setLayoutManager(layoutManager);
        new DownloadPlanetsListTask().execute(
                getResources().getString(R.string.url) + PLANETS_URL);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private JSONArray objects;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView planetName;
            TextView planetPosition;
            TextView planetSize;
            ImageView planetImage;

            ViewHolder(View v) {
                super(v);
                planetName = (TextView) v.findViewById(R.id.planetName);
                planetSize = (TextView) v.findViewById(R.id.planetSize);
                planetPosition = (TextView) v.findViewById(R.id.planetPosition);
                planetImage = (ImageView) v.findViewById(R.id.planetImage);
            }
        }

        MyAdapter(JSONArray objects) {
            this.objects = objects;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.planets_list_item, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PlanetViewActivity.class);
                    int itemPosition = planetsRecyclerView.getChildLayoutPosition(v);
                    try {
                        intent.putExtra("planet url",
                                objects.getJSONObject(itemPosition).getString("url"));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                JSONObject planetObject = objects.getJSONObject(position);
                Log.d("json", planetObject.toString());
                String name = planetObject.getString("name");
                JSONObject posObject = planetObject.getJSONObject("position");
                String pos = "(" + posObject.getString("numberOfSystem") + ", " +
                        posObject.getString("numberOfPlanet") + ")";
                int planetNumber = posObject.getInt("numberOfPlanet");

                if(planetNumber < 5) {
                    holder.planetImage.setImageDrawable(
                            getResources().getDrawable(R.drawable.planet_hot));
                } else if (planetNumber < 10) {
                    holder.planetImage.setImageDrawable(
                            getResources().getDrawable(R.drawable.planet));
                } else  {
                    holder.planetImage.setImageDrawable(
                            getResources().getDrawable(R.drawable.planet_cold));
                }

                holder.planetName.setText(name);
                holder.planetSize.setText("32");
                holder.planetPosition.setText(pos);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return objects.length();
        }
    }


    private class DownloadPlanetsListTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            return restTemplate.getForObject(params[0], String.class);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray planets = new JSONArray(s);
                Log.d("json", planets.toString());
                planetAdapter = new MyAdapter(planets);
                planetsRecyclerView.setAdapter(planetAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
