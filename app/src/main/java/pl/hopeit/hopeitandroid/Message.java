package pl.hopeit.hopeitandroid;

public class Message {

    private String title;
    private String content;
    private String pictureURL;
    private boolean isFromAdmin;

    public Message(String title, String content, boolean isFromAdmin) {
        this.title = title;
        this.content = content;
        this.isFromAdmin = isFromAdmin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFromAdmin(boolean fromAdmin) {
        isFromAdmin = fromAdmin;
    }

    public boolean isFromAdmin() {
        return isFromAdmin;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getPictureURL() {
        return pictureURL;
    }
}
