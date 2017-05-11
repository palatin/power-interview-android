package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Игорь on 11.04.2017.
 */

public class Action implements Parcelable, Serializable {


    private static final long serialVersionUID = -5303257196515386261L;
    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        try {
            if(((Action)obj).getKey().equals(getKey()))
                equals = true;
        }
        finally {
            return equals;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    public Action() {
    }

    protected Action(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel source) {
            return new Action(source);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };

}
