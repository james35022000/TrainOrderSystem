package jcchen.taitiesyunbao.Entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCChen on 2017/7/15.
 */

public class StoreInfo {

    private String ID;
    private String Name;
    private String Address_tw, Address_en;
    private String Tel;
    private String Rate;
    private String Info;
    private String Latitude;
    private String Longitude;
    private List<String> Types;
    private String Station;
    private String Area;
    private List<ImageAttr> Image;
    private String _storeID;
    private int ReviewAmount;

    public StoreInfo() {}

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress(final int language) {
        if(language == Constant.LANGUAGE_EN)
            return this.Address_en;
        else
            return this.Address_tw;
    }

    public void setAddress(String Address_tw, String Address_en) {
        this.Address_tw = Address_tw;
        this.Address_en = Address_en;
    }

    public String getTel() {
        return this.Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getRate() {
        if(Rate.equals("null"))
            Rate = "0";
        return this.Rate;
    }

    public void setRate(String Rate) {
        this.Rate = Rate;
    }

    public String getInfo() {
        return this.Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public String getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public List<String> getTypes() {
        if(Types == null)
            this.Types = new ArrayList<>();
        return new ArrayList<>(this.Types);
    }

    public void setTypes(List<String> Types) {
        this.Types = Types;
    }

    public String getStation() {
        return this.Station;
    }

    public void setStation(String Station) {
        this.Station = Station;
    }

    public String getArea() {
        return this.Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public List<ImageAttr> getImage() {
        if(this.Image == null)
            this.Image = new ArrayList<>();
        return new ArrayList<>(this.Image);
    }

    public void setImage(List<ImageAttr> Image) {
        this.Image = Image;
    }

    public String getStoreID() {
        return this._storeID;
    }

    public void setStoreID(String _storeID) {
        this._storeID = _storeID;
    }

    public int getReviewAmount() {
        return ReviewAmount;
    }

    public void setReviewAmount(int ReviewAmount) {
        this.ReviewAmount = ReviewAmount;
    }
}
