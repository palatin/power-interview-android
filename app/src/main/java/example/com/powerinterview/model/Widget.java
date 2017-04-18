package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 06.04.2017.
 */

public class Widget implements Parcelable {


    private String className;

    private List<Attribute> attributes;

    private List<Action> actions;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    public Widget() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.className);
        dest.writeList(this.attributes);
        dest.writeList(this.actions);
    }

    protected Widget(Parcel in) {
        this.className = in.readString();
        this.attributes = new ArrayList<Attribute>();
        this.actions = new ArrayList<Action>();
        in.readList(this.attributes, Attribute.class.getClassLoader());
        in.readList(this.actions, Action.class.getClassLoader());
    }

    public static final Creator<Widget> CREATOR = new Creator<Widget>() {
        @Override
        public Widget createFromParcel(Parcel source) {
            return new Widget(source);
        }

        @Override
        public Widget[] newArray(int size) {
            return new Widget[size];
        }
    };

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
