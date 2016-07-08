package edu.hm.muse.controller;

import org.springframework.jdbc.core.RowMapper;

import stuff.Text;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TextMapper implements RowMapper<Text> {

    @Override
    public Text mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Text(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("content"), resultSet.getInt("projectID"));
    }
}
