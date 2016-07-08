package edu.hm.muse.controller;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import stuff.Project;

public class ProjectMapper implements RowMapper<Project>{
	
	@Override
	public Project mapRow(ResultSet resultSet, int i) throws SQLException {
		Project p = new Project();
		p.
	}
}
