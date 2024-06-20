package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Appointment;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AppointmentDAO implements IDAO<Appointment, Boolean, Integer> {


    @Override
    public Appointment get(Integer i) {
        return null;
    }

    @Override
    public List<Appointment> getAll() {
        return null;
    }

    @Override
    public void add(Appointment appointment) {

    }

    @Override
    public void update(Appointment appointment) {

    }

    @Override
    public Boolean delete(Integer byId) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    public Boolean deleteByPatientId(Integer byId) {

        String sql = String.format("delete from %s where doctorId=? OR byId=?", Table.APPOINTMENT);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            pst.setInt(1, byId);
            pst.setInt(2, byId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }
}
