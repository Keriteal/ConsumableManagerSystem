package dao;

import model.ConsumableBean;
import model.RecordBean;
import model.UserBean;
import model.protobuf.RecordProto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;
import utils.ProtobufUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {
    private static final String SqlCommit = "INSERT INTO " + RecordBean.TABLE_NAME + "(" +
            RecordBean.COLUMN_USER + "," + RecordBean.COLUMN_ITEM + "," + RecordBean.COLUMN_COUNT + "," +
            RecordBean.COLUMN_TIME_COMMIT + ") " +
            "VALUES (?,?,?,NOW());";
    private static final String SqlConfirm = "UPDATE " + RecordBean.TABLE_NAME + " " +
            "SET " + RecordBean.COLUMN_ADMIN + "=?, " +
            RecordBean.COLUMN_TIME_CONFIRM + "=NOW() " +
            "WHERE " + RecordBean.COLUMN_ID + "=?";
    private static final String SqlUnconfirmed = "SELECT\n" +
            "\tconsumables_record.cr_id cr_id,\n" +
            "\tconsumables_user.cu_name cu_name,\n" +
            "\tconsumables_item.ci_id ci_id,\n" +
            "\tconsumables_item.ci_name ci_name,\n" +
            "\tconsumables_record.cr_count cr_count,\n" +
            "\tconsumables_record.cr_time_commit \n" +
            "FROM\n" +
            "\tconsumables_item,\n" +
            "\tconsumables_user,\n" +
            "\tconsumables_record \n" +
            "WHERE\n" +
            "\tconsumables_item.ci_id = consumables_record.ci_id \n" +
            "\tAND consumables_user.cu_id = consumables_record.cu_id \n" +
            "\tAND ISNULL( consumables_record.ca_id ) \n" +
            "\tAND ISNULL(\n" +
            "\tconsumables_record.cr_time_confirm)";
    private static final String SqlAll = "SELECT * FROM " + RecordBean.TABLE_NAME;

    private static final Logger logger = LogManager.getLogger();

    public List<RecordBean> listAll() {
        List<RecordBean> list = new ArrayList<>();
        try (Connection connection = HikariCpUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SqlUnconfirmed);
            while (rs.next()) {
                RecordBean bean = new RecordBean();
                bean.setId(rs.getInt(RecordBean.COLUMN_ID));
                bean.setUserId(rs.getInt(RecordBean.COLUMN_USER));
                bean.setItemId(rs.getInt(RecordBean.COLUMN_ITEM));
                bean.setCount(rs.getInt(RecordBean.COLUMN_COUNT));
                bean.setAdminId(rs.getInt(RecordBean.COLUMN_ADMIN));
                bean.setCommitTime(rs.getTimestamp(RecordBean.COLUMN_TIME_COMMIT));
                bean.setConfirmTime(rs.getTimestamp(RecordBean.COLUMN_TIME_CONFIRM));
                list.add(bean);
            }
        } catch (SQLException exception) {
            logger.fatal(exception.getMessage());
        }
        return list;
    }

    public List<RecordProto.RecordInfo> listUnconfirmed() {
        List<RecordProto.RecordInfo> list = new ArrayList<>();
        RecordProto.RecordInfo.Builder builder = RecordProto.RecordInfo.newBuilder();
        try (Connection connection = HikariCpUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SqlUnconfirmed);
            while (rs.next()) {
                builder
                        .setRecordId(rs.getInt(RecordBean.COLUMN_ID))
                        .setItemName(rs.getString(ConsumableBean.COLUMN_NAME))
                        .setItemId(rs.getInt(ConsumableBean.COLUMN_ID))
                        .setUserName(rs.getString(UserBean.COLUMN_NAME))
                        .setCount(rs.getInt(RecordBean.COLUMN_COUNT))
                        .setCommitTime(ProtobufUtils.NativeTimestampToProtoTimestamp(rs.getTimestamp(RecordBean.COLUMN_TIME_COMMIT)));
                list.add(builder.build());
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return list;
    }

    public boolean commit(RecordBean record) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlCommit)) {
            logger.debug(SqlCommit);
            ps.setInt(1, record.getUserId());
            ps.setInt(2, record.getItemId());
            ps.setInt(3, record.getCount());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }

    public boolean confirm(RecordBean record) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlConfirm)) {
            logger.debug(SqlConfirm);
            ps.setInt(1, record.getId());
            ps.setInt(2, record.getAdminId());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }
}
