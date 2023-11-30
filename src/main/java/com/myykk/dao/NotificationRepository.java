package com.myykk.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.myykk.model.EmailDTO;
import com.myykk.model.IssueDTO;


@Repository
public class NotificationRepository {

	private static final Logger log = LoggerFactory.getLogger(NotificationRepository.class);
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String selectEmailByFuncIdQuery;
	private final String updateStatusByEmailIdQuery;
	private final String updateStatusByEmailIdAndCustnumAndDocNumQuery;
	private final String selectIssueByIssueIdQuery;
	
	public NotificationRepository(NamedParameterJdbcTemplate jdbcTemplate,
							  @Value("${select.email.by.funcid}") String selectEmailByFuncIdQuery,
							  @Value("${update.status.by.emailid}") String updateStatusByEmailIdQuery,
							  @Value("${update.status.by.emailidandcustnumanddocnum}") String updateStatusByEmailIdAndCustnumAndDocNumQuery,
							  @Value("${select.issue.by.issueid}") String selectIssueByIssueIdQuery
							) {
		this.jdbcTemplate = jdbcTemplate;
		this.selectEmailByFuncIdQuery = selectEmailByFuncIdQuery;
		this.updateStatusByEmailIdQuery = updateStatusByEmailIdQuery;
		this.updateStatusByEmailIdAndCustnumAndDocNumQuery = updateStatusByEmailIdAndCustnumAndDocNumQuery;
		this.selectIssueByIssueIdQuery = selectIssueByIssueIdQuery;
	}

	public List<EmailDTO> getEmailListForFuncId(String funcId) {		
		return jdbcTemplate.query(selectEmailByFuncIdQuery, new MapSqlParameterSource()
				.addValue("funcid", funcId), new EmailRowMapper());
	}
	
	public void updateMailStatus(String emailid, String status, String createtime, Timestamp sentTime) {
		 log.debug("Entering updateMailDetails method of class with status : " +  status);
		 SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("STATUSFLG", status)
		            .addValue("senttime", sentTime)
		            .addValue("EMAILID", emailid)
		            .addValue("CREATETIME", createtime);
		 
		 int i= jdbcTemplate.update(updateStatusByEmailIdQuery, mapSqlParameterSource);
		 log.debug("inserted into emailf result="+i);
	}

	
	
	public void updateInvoiceDetailStatus(String emailid,String custno,String docnum,String status,String createtime) {
							
		SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("STATUSFLG", status)
		            .addValue("EMAILID", emailid)
		            .addValue("CUSTNUM", custno)
		            .addValue("DOCNUM", docnum)
		            .addValue("CREATETIME", createtime);
		int i=jdbcTemplate.update(updateStatusByEmailIdAndCustnumAndDocNumQuery, mapSqlParameterSource);
		log.debug("inserted into emailf result="+i);
		
	}

	public IssueDTO getIssueDetails(String isuid) {
		return jdbcTemplate.query(selectIssueByIssueIdQuery, new MapSqlParameterSource()
				.addValue("isuid", isuid), new IssueExtractor());
	}

}
