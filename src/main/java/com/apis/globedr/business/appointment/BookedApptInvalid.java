package com.apis.globedr.business.appointment;


public class BookedApptInvalid extends BookedApt{

    @Override
    protected void chooseDoctorInRoom() {

    }

    @Override
    protected void prepare() {
        chooseOrg();
        chooseFirstService();
    }
}
