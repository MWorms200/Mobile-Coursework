package gcuS1508180.mpd.bgsdatastarter;

/***
 * Michael Worms
 * S1508180
 * 4th Year Computing
 * MPD
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Earthquake> mFeedModelList;
    private Button mFetchMapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchFeedTask().execute((Void) null);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFetchMapButton = (Button) findViewById(R.id.fetchMapButton);
        mFetchMapButton.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Earthquake> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
         String title = null;
         String description = null;
         Double lat = null;
         Double lng = null;
        boolean isItem = false;
        List<Earthquake> items = new ArrayList<>();
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();
                String name = xmlPullParser.getName();
                if(name == null)
                    continue;
                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }
                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }
                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                    Log.d("Main Activity", description);
                }else if (name.equalsIgnoreCase("geo:lat")){
                    lat = Double.parseDouble(result);
                }else if(name.equalsIgnoreCase("geo:long")){
                    lng=Double.parseDouble(result);
                }
                if (title != null && description != null && lat!=null && lng!=null ) {
                    if(isItem) {
                        Earthquake item = new Earthquake(title, description, lat, lng);
                        items.add(item);
                    }
                    title=null;
                    description=null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

        public void onClick(View view) {
            if (view == mFetchMapButton) {
                Intent intent = new Intent(this, MapsActivity.class);
                Earthquake e;
                for (int i = 0; i < mFeedModelList.size(); i++) {
                    e = mFeedModelList.get(i);
                    String description = e.getDescription();
                    Double lat = e.getLat();
                    Double lng = e.getLng();
                    intent.putExtra(i+"description", description);
                    intent.putExtra(i+"lat", lat);
                    intent.putExtra(i+"lng", lng);

//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse("geo:54.261124,-2.381588"));
                    startActivity(intent);
                }

            }
        }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            urlLink = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            if (success) {
                // Fill RecyclerView
                mRecyclerView.setAdapter(new Adapter(mFeedModelList));
            } else {
                Toast.makeText(MainActivity.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}