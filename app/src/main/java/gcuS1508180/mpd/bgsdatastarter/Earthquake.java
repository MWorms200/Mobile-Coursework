package gcuS1508180.mpd.bgsdatastarter;
public class Earthquake{


    public String title;
    public String description;
    public Double lat;
    public Double lng;



    public Earthquake(String title,String description, Double lat, Double lng) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lng = lng;

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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


}