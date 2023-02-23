package com.myykk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myykk.model.UserDetailsDTO;


@Repository
public class UserAuthenticationRepository {

	private static final Logger log = LoggerFactory.getLogger(UserAuthenticationRepository.class);
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String selectUserByEmailQuery;
	
	public UserAuthenticationRepository(NamedParameterJdbcTemplate jdbcTemplate,
							  @Value("${select.user.by.emailid}") String selectUserByEmailQuery
							) {
		this.jdbcTemplate = jdbcTemplate;
		this.selectUserByEmailQuery = selectUserByEmailQuery;
	}

	public UserDetailsDTO getUserDetailsForEmailId(String emailId) {
		return jdbcTemplate.query(selectUserByEmailQuery, new MapSqlParameterSource()
				.addValue("emailid", emailId), new UserDetailsExtractor());
	}

}
