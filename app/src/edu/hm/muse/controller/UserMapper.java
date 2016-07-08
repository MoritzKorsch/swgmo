package edu.hm.muse.controller;

import org.springframework.jdbc.core.RowMapper;

import stuff.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("pwd"), resultSet.getBytes("salt"));
    }
}
