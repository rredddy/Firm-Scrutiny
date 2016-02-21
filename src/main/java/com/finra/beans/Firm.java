package com.finra.beans;

/**
 * Created by ravender on 2/20/2016.
 */
public class Firm {

    private Integer firmID;

    public Integer getFirmID() {
        return firmID;
    }

    public void setFirmID(Integer firmID) {
        this.firmID = firmID;
    }

    @Override
    public String toString() {
        return "Firm{" +
                "firmID=" + firmID +
                '}';
    }
}
