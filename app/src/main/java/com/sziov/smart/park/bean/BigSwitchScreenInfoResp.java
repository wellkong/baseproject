package com.sziov.smart.park.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author willkong
 * Date  2020-01-03
 */
public class BigSwitchScreenInfoResp implements Parcelable {
    /**
     * type : 0
     * parkId : 1
     * cameraDtoList : [{"id":35,"status":1}]
     */

    private int type;
    private int parkId;
    private List<CameraDtoList> cameraDtoList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public List<CameraDtoList> getCameraDtoList() {
        return cameraDtoList;
    }

    public void setCameraDtoList(List<CameraDtoList> cameraDtoList) {
        this.cameraDtoList = cameraDtoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.parkId);
        dest.writeTypedList(this.cameraDtoList);
    }

    public BigSwitchScreenInfoResp() {
    }

    protected BigSwitchScreenInfoResp(Parcel in) {
        this.type = in.readInt();
        this.parkId = in.readInt();
        this.cameraDtoList = in.createTypedArrayList(CameraDtoList.CREATOR);
    }

    public static final Parcelable.Creator<BigSwitchScreenInfoResp> CREATOR = new Parcelable.Creator<BigSwitchScreenInfoResp>() {
        @Override
        public BigSwitchScreenInfoResp createFromParcel(Parcel source) {
            return new BigSwitchScreenInfoResp(source);
        }

        @Override
        public BigSwitchScreenInfoResp[] newArray(int size) {
            return new BigSwitchScreenInfoResp[size];
        }
    };
}