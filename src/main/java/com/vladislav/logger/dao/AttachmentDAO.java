package com.vladislav.logger.dao;

import com.vladislav.logger.models.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AttachmentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttachmentDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewAttachment(Attachment attachment){
        final String INSERT_SQL = "INSERT INTO attachments (action_id, location) value (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setInt(1, attachment.getActionId());
                        ps.setString(2, attachment.getLocation());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

}
