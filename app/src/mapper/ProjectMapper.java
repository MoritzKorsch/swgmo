package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import stuff.Project;

public class ProjectMapper implements RowMapper<Project> {

	@Override
	public Project mapRow(ResultSet r, int i) throws SQLException {
		Project p = new Project( 
				r.getInt("ID"), 
				r.getString("name"), 
				r.getString("description"), 
				r.getInt("ownerID") );
		return p;
	}

}
