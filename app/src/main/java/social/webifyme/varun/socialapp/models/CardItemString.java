package social.webifyme.varun.socialapp.models;

/**
 * Created by KottlandPro TET on 3/3/2018.
 */

public class CardItemString {

    private String mTextResource;
    private String mTitleResource;
    private int mImageResource;

    public CardItemString(String title, String text,int image) {
        mTitleResource = title;
        mTextResource = text;
        mImageResource = image;
    }



    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public int getmImage() {
        return mImageResource;
    }
}
