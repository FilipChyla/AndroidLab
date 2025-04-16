package pl.pollub.android.myapplication.phonesExercise.data;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class PhoneEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String manufacturer;
    @NonNull
    private String model;
    @NonNull
    private String androidVersion;
    private String site;

    public PhoneEntity(@NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, String site) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.site = site;
    }

    protected PhoneEntity(Parcel in) {
        id = in.readLong();
        manufacturer = in.readString();
        model = in.readString();
        androidVersion = in.readString();
        site = in.readString();
    }

    public static final Creator<PhoneEntity> CREATOR = new Creator<>() {
        @Override
        public PhoneEntity createFromParcel(Parcel in) {
            return new PhoneEntity(in);
        }

        @Override
        public PhoneEntity[] newArray(int size) {
            return new PhoneEntity[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @NonNull
    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(@NonNull String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(manufacturer);
        dest.writeString(model);
        dest.writeString(androidVersion);
        dest.writeString(site);
    }
}
