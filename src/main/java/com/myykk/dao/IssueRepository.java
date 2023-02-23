package com.myykk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myykk.model.IssueDTO;


@Repository
public class IssueRepository {

	private static final Logger log = LoggerFactory.getLogger(IssueRepository.class);
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String selectIssueByIssueidQuery;
	
	public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate,
							  @Value("${select.issue.by.issueid}") String selectIssueByIssueidQuery
							) {
		this.jdbcTemplate = jdbcTemplate;
		this.selectIssueByIssueidQuery = selectIssueByIssueidQuery;
	}

	public IssueDTO getIssueForIssueId(String issueid) {
		return jdbcTemplate.query(selectIssueByIssueidQuery, new MapSqlParameterSource()
				.addValue("isuid", issueid), new IssueExtractor());
	}

}
