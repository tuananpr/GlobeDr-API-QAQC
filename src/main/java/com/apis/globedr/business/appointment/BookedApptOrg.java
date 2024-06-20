package com.apis.globedr.business.appointment;

public class BookedApptOrg extends BookedApt{

    @Override
    protected void chooseDoctorInRoom() {
        // Nothing here
    }

    @Override
    protected void prepare() {
        choosePatient();
        chooseOrg();
        chooseFirstService();
        chooseTime();
    }
}
