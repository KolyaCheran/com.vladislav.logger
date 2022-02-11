package com.vladislav.logger.dao;

import com.vladislav.logger.helpers.TimeHelper;
import com.vladislav.logger.models.Attachment;
import com.vladislav.logger.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class AttachmentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttachmentDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewAttachment(Attachment attachment){
        final String INSERT_SQL = "INSERT INTO attachments (action_id, location, timestamp ) value (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setInt(1, attachment.getActionId());
                        ps.setString(2, attachment.getLocation());
                        ps.setInt(3, (int) TimeHelper.getUnixTimeStamp());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Attachment getAttachment(int attachmentId) {
        List<Attachment> attachments = jdbcTemplate.query("SELECT * FROM attachments WHERE id=? ORDER BY id ASC",
                (rs, rowNum) -> new Attachment(rs.getInt("id"),
                       rs.getString("location")),
                new Integer[]{attachmentId});
        return attachments.get(0);
    }

    public void removeOldAttachments(int timestamp){
        final String DELETE_SQL = "DELETE FROM attachments where timestamp<?";
        jdbcTemplate.update(DELETE_SQL, timestamp);
    }
}
