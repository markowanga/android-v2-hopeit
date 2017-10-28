package pl.hopeit.hopeitandroid.model;

/**
 * Created by akruk on 28.10.17.
 */

public class LoginResponse {

    public String imgUrl;
    public String name;

    public LoginResponse(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
