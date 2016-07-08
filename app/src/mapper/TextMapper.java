package mapper;

import org.springframework.jdbc.core.RowMapper;

import stuff.Text;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TextMapper implements RowMapper<Text> {

    @Override
    public Text mapRow(ResultSet r, int i) throws SQLException {
        return new Text(
        		r.getInt("ID"), 
        		r.getString("name"), 
        		r.getString("content"), 
        		r.getInt("projectID"));
    }
}

