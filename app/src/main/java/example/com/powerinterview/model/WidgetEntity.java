package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 06.04.2017.
 */

public class WidgetEntity implements Parcelable, Serializable {


    private static final long serialVersionUID = 8180624971897472123L;
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


    public WidgetEntity() {
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

    protected WidgetEntity(Parcel in) {
        this.className = in.readString();
        this.attributes = new ArrayList<Attribute>();
        this.actions = new ArrayList<Action>();
        in.readList(this.attributes, Attribute.class.getClassLoader());
        in.readList(this.actions, Action.class.getClassLoader());
    }

    public static final Creator<WidgetEntity> CREATOR = new Creator<WidgetEntity>() {
        @Override
        public WidgetEntity createFromParcel(Parcel source) {
            return new WidgetEntity(source);
        }

        @Override
        public WidgetEntity[] newArray(int size) {
            return new WidgetEntity[size];
        }
    };

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
