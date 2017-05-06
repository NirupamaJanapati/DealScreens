package com.example.pramod.deals_screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.sql.Types.NULL;

public class ShowHealthAndFitness extends AppCompatActivity {

    private ListView lv_Deals;
    private ArrayList<DealModel> dealModelList1 = new ArrayList<>();
    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_health_and_fitness);
        handler = new Handler(getApplicationContext().getMainLooper());

        lv_Deals = (ListView) findViewById(R.id.lvDeals);
        new BackgroundTask().execute("https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_201236_212556_0&lat=37.3370102&lng=-121.88160040000002&filters=category:health-and-fitness&offset=0&limit=25");

        lv_Deals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                String view_title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String view_status = ((TextView) view.findViewById(R.id.status)).getText().toString();
                String view_redemption_location = ((TextView) view.findViewById(R.id.redemptionLocation)).getText().toString();

                System.out.println("view_title: " + view_title);
                System.out.println("view_status: " + view_status);
                System.out.println("view_redemption_location: " + view_redemption_location);

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("title", view_title);
                intent.putExtra("status", view_status);
                intent.putExtra("redemptionlocation", view_redemption_location);

                view.getContext().startActivity(intent);
            }
        });

    }

    public class BackgroundTask extends AsyncTask<String, String, List<DealModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public List<DealModel> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObjeect = new JSONObject(finalJson);
                JSONArray parentArray = parentObjeect.getJSONArray("deals");

                List<DealModel> dealModelList = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {
                    DealModel dealModel = new DealModel();
                    //get JSON object
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    //find values and assign them to variables
                    dealModel.setTitle(finalObject.getString("title"));
                    dealModel.setAnnouncementTitle(finalObject.getString("announcementTitle"));
                    dealModel.setSmallImageUrl(finalObject.getString("smallImageUrl"));
                    dealModel.setStatus(finalObject.getString("status"));
                    dealModel.setType(finalObject.getString("type"));
                    dealModel.setStartAt(finalObject.getString("startAt"));
                    dealModel.setEndAt(finalObject.getString("endAt"));
                    dealModel.setRedemptionLocation(finalObject.getString("redemptionLocation"));

                    Log.i("log_tag", "title" + finalObject.getString("title") +
                            ", announcementTitle" + finalObject.getString("announcementTitle") +
                            ", smallImageUrl" + finalObject.getString("smallImageUrl") +
                            ", status" + finalObject.getString("status") +
                            ", type" + finalObject.getString("type") +
                            ", startAt" + finalObject.getString("startAt") +
                            ", endAt" + finalObject.getString("endAt") +
                            ", redemptionlocation" + finalObject.getString("redemptionLocation")
                    );

                    //add the final object to the List
                    dealModelList.add(dealModel);
                }
                return dealModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<DealModel> result) {
            super.onPostExecute(result);
            DealAdapter adapter = new DealAdapter(getApplicationContext(), R.layout.row, result);
            lv_Deals.setAdapter(adapter);
        }

        public class DealAdapter extends ArrayAdapter {

            public List<DealModel> dealModelList1;
            private int resource;
            private LayoutInflater inflater;

            public DealAdapter(Context context, int resource, List<DealModel> objects) {
                super(context, resource, objects);
                this.dealModelList1 = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.row, null);
                }

                ImageView imageButton;
                final TextView title;
                TextView announcementtitle;
                final TextView status;
                TextView type;
                TextView startAt;
                TextView endAt;
                final TextView redemptionLocation;
                final Button btn_redeem;

                imageButton = (ImageView) convertView.findViewById(R.id.imageButton);
                title = (TextView) convertView.findViewById(R.id.title);
                //announcementtitle =(TextView) convertView.findViewById(R.id.announcementtitle);
                status = (TextView) convertView.findViewById(R.id.status);
                //type =(TextView) convertView.findViewById(R.id.type);
                //startAt =(TextView) convertView.findViewById(R.id.startAt);
                //endAt =(TextView) convertView.findViewById(R.id.endAt);
                redemptionLocation = (TextView) convertView.findViewById(R.id.redemptionLocation);
                btn_redeem = (Button) convertView.findViewById((R.id.redeem));

                title.setText(dealModelList1.get(position).getTitle());
                //announcementtitle.setText(dealModelList.get(position).getAnnouncementTitle());
                status.setText(dealModelList1.get(position).getStatus());
                //type.setText("Type: "+dealModelList.get(position).getType());
                //startAt.setText("Starts At: "+dealModelList.get(position).getStartAt());
                //endAt.setText("Ends At: "+dealModelList.get(position).getEndAt());
                redemptionLocation.setText(dealModelList1.get(position).getRedemptionLocation());
                /*
                btn_redeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                final String id = String.valueOf(position+1);

                                Toast.makeText(getContext(),
                                        "Details Saved " + " " +" " + id +" " + date
                                                + dealModelList1.get(position).getTitle()
                                                + dealModelList1.get(position).getStatus()
                                                + dealModelList1.get(position).getRedemptionLocation()
                                        , Toast.LENGTH_LONG).show();
                                        }
                            }
                        });
                    }
                }); */
                return convertView;
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}

