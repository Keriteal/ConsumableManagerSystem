package dao;

import model.ConsumableBean;
import utils.HikariCpUtils;
import utils.SqlLanguageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsumableDAO {
    public ArrayList<ConsumableBean> listAll() {
        Connection connection = null;
        PreparedStatement ps = null;
        ArrayList<ConsumableBean> list = new ArrayList<>();
        try {
            connection = HikariCpUtils.getConnection();
            ps = connection.prepareStatement(SqlLanguageUtils.generateQueryAll(ConsumableBean.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null){
                    ps.close();
                }
                if(connection !=null){
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return list;
    }

    public int delete(ConsumableBean deleted) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = 0;
        try {
            connection = HikariCpUtils.getConnection();
            ps = connection.prepareStatement(SqlLanguageUtils.generateDelete(deleted));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if(ps!=null){
                    ps.close();
                }
                if(connection !=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
}
