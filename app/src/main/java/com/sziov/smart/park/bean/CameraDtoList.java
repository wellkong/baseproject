package com.sziov.smart.park.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.bean
 * @Author: willkong
 * @CreateDate: 2020/9/29 18:09
 * @Description: 摄像头
 */
public class CameraDtoList implements Parcelable {
    /**
     * id : 35
     * status : 1
     */

    private int id;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.status);
    }

    public CameraDtoList() {
    }

    protected CameraDtoList(Parcel in) {
        this.id = in.readInt();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<CameraDtoList> CREATOR = new Parcelable.Creator<CameraDtoList>() {
        @Override
        public CameraDtoList createFromParcel(Parcel source) {
            return new CameraDtoList(source);
        }

        @Override
        public CameraDtoList[] newArray(int size) {
            return new CameraDtoList[size];
        }
    };
}
