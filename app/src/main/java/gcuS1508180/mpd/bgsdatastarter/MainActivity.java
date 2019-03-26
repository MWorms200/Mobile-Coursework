
/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Michael Worms
// Student ID           S1508180
// Programme of Study   BSc Hons
//

// Update the package name to include your Student Identifier
package gcuS1508180.mpd.bgsdatastarter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private XmlPullParser xpp;
    private LinkedList alist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        LinkedList<Earthquake> alist = null;

        // More Code goes here
    }


    public void onClick(View aview) {
        startProgress();
    }

    public void startProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    }

   // Need separate thread to access the internet resource over network
   // Other neater solutions should be adopted in later iterations.
private class Task implements Runnable
{
    private String url;

    public Task(String aurl)
   {
        url = aurl;
    }
    @Override
    public void run()
    {
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        Log.e("MyTag","in run");
       try
       {
            Log.e("MyTag","in try");
            aurl = new URL(url);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            while ((inputLine = in.readLine()) != null)
            {
                result = result + inputLine;
              // Log.e("MyTag",inputLine);

            }
            xpp = null;
            in.close();
            //System.out.print(result);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput( new StringReader(result));
       }
       catch (IOException | XmlPullParserException ae)
       {
           Log.e("MyTag", "ioexception");
       }


        try
        {
            int eventType = xpp.getEventType();
            Earthquake earthquake = null;
            LinkedList <Earthquake> alist = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                   //Log.e("MyTag", "Found Start Tag");
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        alist  = new LinkedList<>();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        //Log.e("MyTag","Item Start Tag found");
                        earthquake = new Earthquake();

                    }
                    else if(earthquake!=null){
                        if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            if (earthquake!=null){
                                //Log.e("MyTag","title is " + temp);
                                earthquake.setTitle(temp);}
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                if(earthquake!=null){
                                    //Log.e("MyTag","Description is " + temp);
                                    earthquake.setDescription(temp);}
                            }
                            else
                                // Check which Tag we have
                                if (xpp.getName().equalsIgnoreCase("link"))
                                {
                                    // Now just get the associated text
                                    String temp = xpp.nextText();
                                    // Do something with text

                                    //Log.e("MyTag","link is " + temp);
                                    earthquake.setLink(temp);
                                }
                                else if (xpp.getName().equalsIgnoreCase("pubDate")){
                                    String temp = xpp.nextText();

                                    //Log.e("MyTag", "pubDate is " + temp);
                                    earthquake.setPubDate(temp);
                                }
                                else if (xpp.getName().equalsIgnoreCase("category")){
                                    String temp = xpp.nextText();

                                    //Log.e("MyTag", "category is " + temp);
                                    earthquake.setCategory(temp);
                                }
                                else if (xpp.getName().equalsIgnoreCase("geo:lat")){
                                    String temp = xpp.nextText();

                                   // Log.e("MyTag", "Latitude is " + temp);
                                    earthquake.setGeoLat(temp);
                                }
                                else if (xpp.getName().equalsIgnoreCase("get:long")){
                                    String temp = xpp.nextText();

                                    //Log.e("MyTag", "Longitude is " + temp);
                                    earthquake.setGeoLng(temp);
                                }
                    }

                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","earthquake is " + earthquake.toString());
                        alist.add(earthquake);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = alist.size();
                        Log.e("MyTag","channel size is " + size);
                        done=true;
                    }
                }
                // Get the next event
                eventType = xpp.next();
            } // End of while
            //return alist;
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error " + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
        Log.e("MyTag","End document");
        // Now update the TextView to display raw XML data
        // Probably not the best way to update TextView
        // but we are just getting started !
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run() {
                Log.d("UI thread", "I am the UI thread");
                //rawDataDisplay.setText(result);


            }
        });
    }
}

    private void printEarthquake(ArrayList<Earthquake> alist) {
        StringBuilder builder = new StringBuilder();

        for (Earthquake earthquake : alist) {
            builder.append(earthquake.title).append("\n").
                    append(earthquake.description).append("\n").
                    append(earthquake.category).append("\n") .
                    append(earthquake.geoLat).append(earthquake.geoLng).append("\n\n");
        }

        //rawDataDisplay.setText(builder.toString());
    }
}




