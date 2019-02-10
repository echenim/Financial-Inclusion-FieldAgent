package k36tee.agent.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by myron echenim  on 8/16/16.
 */
public class RequestObject implements Parcelable {
    String id, name;

    public RequestObject() {
    }

    protected RequestObject(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<RequestObject> CREATOR = new Creator<RequestObject>() {
        @Override
        public RequestObject createFromParcel(Parcel in) {
            return new RequestObject(in);
        }

        @Override
        public RequestObject[] newArray(int size) {
            return new RequestObject[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
