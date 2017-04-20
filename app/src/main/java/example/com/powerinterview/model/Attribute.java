package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Игорь on 06.04.2017.
 */

public class Attribute implements Parcelable{


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
            if(((Attribute)obj).getKey().equals(getKey()))
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

    public Attribute() {
    }

    protected Attribute(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel source) {
            return new Attribute(source);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };
}
