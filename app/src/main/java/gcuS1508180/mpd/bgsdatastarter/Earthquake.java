package gcuS1508180.mpd.bgsdatastarter;
public class Earthquake{


    public String title;
    public String description;


    public Earthquake(String title,String description) {
        this.title = title;
        this.description = description;

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