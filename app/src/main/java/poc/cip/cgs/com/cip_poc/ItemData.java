package poc.cip.cgs.com.cip_poc;

/**
 * Created by Rohini on 5/27/2015.
 */
public class ItemData {
    private String title;
    private String imageUrl;
    private boolean scanStatus;
    private int serverStatus;


    public boolean isScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(boolean scanStatus) {
        this.scanStatus = scanStatus;
    }


    public ItemData(String title, String imageUrl) {

        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ItemData(String title, int serverStatus) {

        this.title = title;
        this.serverStatus = serverStatus;
    }


    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }
    // getters & setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
