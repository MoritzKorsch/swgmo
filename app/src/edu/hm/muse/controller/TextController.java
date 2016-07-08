package edu.hm.muse.controller;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class TextController {
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
}
