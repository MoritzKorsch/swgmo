package mapper;

import org.springframework.jdbc.core.RowMapper;

import stuff.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet r, int i) throws SQLException {
        return new User(
        		r.getInt("ID"), 
        		r.getString("name"), 
        		r.getString("pwd"), 
        		r.getBytes("salt"));
    }
}
