package xyz.davidng.xmlreader;

/**
 * Created by admin on 12/16/2015.
 */
public class VNExpress {
    String title;
    String imageLink;

    public VNExpress(String title, String imageLink) {
        this.title = title;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public String getImageLink() {
        return imageLink;
    }
}
