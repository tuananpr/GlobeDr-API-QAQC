package com.apis.globedr.business.appointment;


import com.apis.globedr.apis.AptApi;
import com.rest.core.response.Response;
import org.junit.Assert;

public class BookedApptDoctor extends BookedApt {


    private void validateDoctor() {
        if (getDoctorName() == null) Assert.fail("Please set doctorName to book appointment");
    }


    @Override
    protected void chooseDoctorInRoom() {
        validateDoctor();
        Response rs = AptApi.getInstant().doctorSchedules(getToSig(), getProductServiceSig());
        setDoctorSig(rs.extractAsList(String.format("$.data.list[?(@.isEnable == true)].examinations[*].doctorRooms[?(@.name == '%s')].signature", getDoctorName().trim())).get(0));
        setRoomId(rs.extractAsList(String.format("$.data.list[?(@.isEnable == true)].examinations[*].doctorRooms[?(@.name == '%s')].roomId", getDoctorName().trim())).get(0));
    }

    @Override
    protected void prepare() {
        choosePatient();
        chooseOrg();
        chooseFirstService();
        chooseDoctorInRoom();
        chooseTime();
    }


}
