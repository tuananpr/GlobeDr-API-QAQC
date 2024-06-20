package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.helper.Common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DAO<E, T, K> implements IDAO<E, T, K> {

    private String upperCaseFirstCharacter(String content) {
        return content.substring(0, 1).toUpperCase() + content.substring(1);
    }

    private String remmoveTheLastThreeCharacter(String content) {
        return content.substring(0, content.length() - 3);
    }



    String getCurrentTable() {
        String className = remmoveTheLastThreeCharacter(this.getClass().getSimpleName());
        return className;
    }

    public Boolean delete(DAO d, FieldType fieldType) {
        String sql = String.format("delete from %s where %s", getCurrentTable(), Common.addingConditionFieldsDB(d, fieldType));
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByOrgId(Integer orgId) {
        String sql = String.format("DECLARE @column  AS NVARCHAR(MAX) " + "\n" +
                "DECLARE @query  AS NVARCHAR(MAX) " + "\n" +
                "SELECT  @column = (SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s' AND COLUMN_NAME like 'org%%Id') " + "\n" +
                "set @query = N'Delete from %s where '+@column+'=%s' " + "\n" +
                "EXEC(@query) ", getCurrentTable(), getCurrentTable(), orgId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }
        return false;
    }

    public Boolean deleteByUserId(Integer userId) {
        String sql = String.format("DECLARE @column  AS NVARCHAR(MAX) " + "\n" +
                "DECLARE @query  AS NVARCHAR(MAX) " + "\n" +
                "SELECT  @column = (SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s' AND COLUMN_NAME like 'userId') " + "\n" +
                "set @query = N'Delete from %s where '+@column+'=%s' " + "\n" +
                "EXEC(@query) ", getCurrentTable(), getCurrentTable(), userId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }
        return false;
    }



}
