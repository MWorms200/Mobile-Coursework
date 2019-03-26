package gcuS1508180.mpd.bgsdatastarter;


public class Earthquake {
   public String title;
   public String description;
   public String link;
   public String pubDate;
   public String category;
   public String geoLat;
   public String geoLng;

   public Earthquake(){
       title = "";
       description="";
       link="";
       pubDate="";
       category="";
       geoLat="";
       geoLng="";
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeoLat() {
        return geoLat;
    }


    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(String geoLng) {
        this.geoLng = geoLng;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", category='" + category + '\'' +
                ", geoLat='" + geoLat + '\'' +
                ", geoLng='" + geoLng + '\'' +
                '}';
    }
}
