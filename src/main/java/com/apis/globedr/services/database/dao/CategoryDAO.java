package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.PostDB;
import com.apis.globedr.services.database.entities.Category;
import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.helper.Common;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements IDAO<Category, Boolean, Integer> {


    @Override
    public Category get(Integer categoryId) {
        return null;
    }

    public List<Category> getAll(Category category, FieldType fieldType) {
        String sql = String.format("select TOP 100 categoryId, categoryName, createdById, description from %s where %s ",
                Table.CATEGORY, Common.addingConditionFieldsDB(category, fieldType));
        PreparedStatement pst = null;
        List<Category> list = new ArrayList<>();
        ResultSet rs = null;

        try {
            pst = this.getDB().prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                Category j = new Category(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++));
                list.add(j);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Category> getAll() {
        String sql = String.format("select TOP 100 categoryId, categoryName, createdById, description from %s", Table.CATEGORY);
        PreparedStatement pst = null;
        List<Category> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = this.getDB().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                Category j = new Category(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++));
                list.add(j);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void add(Category user) {

    }

    @Override
    public void update(Category user) {

    }

    @Override
    public Boolean delete(Integer categoryId) {
        String sql = String.format("delete from %s where categoryId=?", Table.CATEGORY);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setInt(1, categoryId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    @Override
    public SqlDB getDB() {
        return PostDB.getInstant();
    }

    public Boolean delete(Category category, FieldType fieldType) {
        String sql = String.format("delete from %s where %s", Table.CATEGORY, Common.addingConditionFieldsDB(category, fieldType));
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByName(String categoryName) {
        String sql = String.format("delete from %s where categoryName like N'%s%%'", Table.CATEGORY, categoryName);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByCreate(Integer createdById) {
        String sql = String.format("delete from %s where createdById=?", Table.CATEGORY);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setInt(1, createdById);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }


}
