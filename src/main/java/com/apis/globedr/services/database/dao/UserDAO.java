package com.apis.globedr.services.database.dao;

import com.apis.globedr.helper.Common;
import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.User;
import com.apis.globedr.services.database.other.FieldType;
import org.springframework.util.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAO implements IDAO<User, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public User get(String gdrLogin) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where gdrLogin=?", Table.USER);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setString(1, gdrLogin);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++));

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
        return null;
    }

    public List<User> getAll(User user, FieldType fieldType) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where %s ",
                Table.USER, Common.addingConditionFieldsDB(user, fieldType));
        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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

    public Boolean setAdmin(String gdrLogin) {

        String sql = String.format("UPDATE %s SET userType = 255 WHERE gdrLogin = '%s'", Table.USER, gdrLogin);
        PreparedStatement pst = null;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserType(String gdrLogin) {
        String sql = String.format("SELECT CONVERT(varchar(10), userType) AS userType FROM %s WHERE gdrLogin = '%s'", Table.USER, gdrLogin);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
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
        return null;
    }

    public List<User> getAllByPhone(String phone) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where phone like '%s'", Table.USER, phone);

        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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

    public List<User> getAllByDisplayName(String displayName) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where displayName like '%s'", Table.USER, displayName);

        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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

    public List<Integer> getUserIdByDisplayName(String listDisplayName) {
        String sql = String.format("select userId from %s where displayName IN ('%s')", Table.USER, listDisplayName);
        PreparedStatement pst = null;
        List<Integer> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("userId"));
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

    public List<User> getAllByEmail(String email) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where email like '%s'", Table.USER, email);
        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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

    public List<User> getAllByCondition(String where) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s WHERE %s", Table.USER, where);
        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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

    public List<User> getAll(String gdrLogin) {
        return getAllByCondition(String.format("gdrLogin like '%s'", gdrLogin));
    }

    public List<User> getAllByGdrLoginOrName(String gdrLogin, String displayName) {
        return getAllByCondition(String.format("gdrLogin like '%s' Or displayName like '%s'", gdrLogin, displayName));
    }


    public List<String> getUserIds(String gdrLogin) {
        List<String> list = new ArrayList<>();
        for (User user : getAll(gdrLogin)) {
            list.add(String.valueOf(user.getUserId()));
        }
        return list;
    }

    @Override
    public List<User> getAll() {
        String sql = String.format("select TOP 100 userId, userType, displayName, gdrLogin, phone, country from %s", Table.USER);
        PreparedStatement pst = null;
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                User j = new User(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
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
    public void add(User user) {

    }

    @Override
    public void update(User user) {

    }


    public boolean clear(String where, List<String> fields) {

        final StringBuilder strSet = new StringBuilder();
        for (String field : fields) {
            strSet.append(field + " = NULL,");
        }

        String sql = String.format("update %s set %s Where %s", Table.USER, Common.removeLastCharacter(strSet.toString()), where);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    public boolean update(String where, Map<String, Object> body) {

        final StringBuilder newData = new StringBuilder();
        for (Map.Entry<String, Object> entry : body.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase("null")) {
                newData.append(String.format("%s = NULL,", entry.getKey(), entry.getValue()));
            } else {
                newData.append(String.format("%s = '%s',", entry.getKey(), entry.getValue()));
            }
        }

        String sql = String.format("update %s set %s Where %s", Table.USER, Common.removeLastCharacter(newData.toString()), where);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean update(String where, User data) {

        final StringBuilder newData = new StringBuilder();
        ReflectionUtils.doWithFields(data.getClass(), field -> {
            field.setAccessible(true);
            if (field.get(data) != null) {
                newData.append(String.format(" %s = '%s',", field.getName(), field.get(data)));
            }
        });

        String sql = String.format("update %s set %s Where %s", Table.USER, Common.removeLastCharacter(newData.toString()), where);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    @Override
    public Boolean delete(String gdrLogin) {
        String sql = String.format("delete from %s where gdrLogin=?", Table.USER);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setString(1, gdrLogin);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean delete(int userId) {
        String sql = String.format("delete from %s where userId=%d", Table.USER, userId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }


    public Boolean deletePostBy(Integer userId) {
        // deletePostMsgDocs
        List<String> deleteUser = new ArrayList<>();
        deleteUser.add(String.format("delete from %s where msgId in (select msgId FROM %s where postId In (Select postId FROM %s WHERE createdById = '%d'))", Table.POST_MSG_DOCS, Table.POST_MSG, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where msgId IN (Select msgId FROM %s WHERE createdById = '%d')", Table.POST_MSG_DOCS, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where msgId in (select msgId FROM %s where postId In (Select postId FROM %s WHERE createdById = '%d'))", Table.POST_MSG_LIKE, Table.POST_MSG, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where userId = '%d'", Table.POST_MSG_LIKE, userId));

        //-- msg
        deleteUser.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById = '%d')", Table.POST_MSG, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where createdById = '%d'", Table.POST_MSG, userId));
        //-- post

        deleteUser.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById = '%d')", Table.POST_ACCESS, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById = '%d')", Table.POST_ACTIVITY, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById = '%d')", Table.POST_FEEDBACK, Table.POST, userId));
        deleteUser.add(String.format("delete from %s where createdById = '%d'", Table.POST, userId));
        PreparedStatement pst;

        try {
            int total = 0;
            for (String cmd : deleteUser) {
                pst = getDB().prepareStatement(cmd);
                total += pst.executeUpdate();
            }
            return total > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


}
