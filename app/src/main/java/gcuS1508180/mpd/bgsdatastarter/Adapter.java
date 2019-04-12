package gcuS1508180.mpd.bgsdatastarter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.FeedModelViewHolder> {

    private List<Earthquake> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {

        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public Adapter(List<Earthquake> rssFeedModels) {
        this.mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earthquake_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {

        final Earthquake earthquake = mRssFeedModels.get(position);

        String[] separated = earthquake.getDescription().split(";");
        double magnitude = Double.parseDouble(separated[4].substring(13));
        String location = separated[1].substring(11);
        String date = separated[0].substring(18);
        String latlong = separated[2].substring(12);
        String depth = separated[3].substring(7);

        if(magnitude <1 ){
            holder.rssFeedView.findViewById(R.id.titleText).setBackgroundColor(Color.GREEN);
            holder.rssFeedView.findViewById(R.id.descriptionText).setBackgroundColor(Color.GREEN);
        }else if(magnitude >=1 && magnitude <2){
            holder.rssFeedView.findViewById(R.id.titleText).setBackgroundColor(Color.YELLOW);
            holder.rssFeedView.findViewById(R.id.descriptionText).setBackgroundColor(Color.YELLOW);
        }else if(magnitude>=2){
            holder.rssFeedView.findViewById(R.id.titleText).setBackgroundColor(Color.RED);
            holder.rssFeedView.findViewById(R.id.descriptionText).setBackgroundColor(Color.RED);
        }
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText(new StringBuilder().append(location).append(",").append(magnitude).toString());
        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(new StringBuilder().append(date).append(",").append(depth).append(",").append(earthquake.lat).append(",").append(earthquake.lng).toString());
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }

}