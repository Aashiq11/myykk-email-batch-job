package com.myykk.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.myykk.model.BatchEmailDTO;


@Repository
public class BatchEmailRepository {

	private static final Logger log = LoggerFactory.getLogger(BatchEmailRepository.class);
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String selectBatchEmailsByEmailStatus;
	private final String updateBatchByxternalSourceAndEmail;
	private final String selectBatchByxternalSourceAndEmail;
	
	public BatchEmailRepository(NamedParameterJdbcTemplate jdbcTemplate,
							  @Value("${select.batch.by.emailstatus}") String selectBatchEmailsByEmailStatus,
							  @Value("${update.batch.by.xternalsourcandemailid}") String updateBatchByxternalSourceAndEmail,
							  @Value("${select.batch.by.xternalsourceandemail}") String selectBatchByxternalSourceAndEmail
							) {
		this.jdbcTemplate = jdbcTemplate;
		this.selectBatchEmailsByEmailStatus = selectBatchEmailsByEmailStatus;
		this.updateBatchByxternalSourceAndEmail = updateBatchByxternalSourceAndEmail;
		this.selectBatchByxternalSourceAndEmail = selectBatchByxternalSourceAndEmail;
	}

	public List<BatchEmailDTO> getBatchEmailList() {		
		return jdbcTemplate.query(selectBatchEmailsByEmailStatus, new MapSqlParameterSource()
				.addValue("UNSENTEMAIL", 0).addValue("RETRYEMAIL", -7), new BatchEmailRowMapper());
	}
	
	
	public void updateHeaderStatus(BatchEmailDTO batchEmail) throws SQLException {
		
		 SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("senttime", batchEmail.getStatus())
		            .addValue("EMAILID", new Timestamp(System.currentTimeMillis()))
		            .addValue("CREATETIME", StringUtils.trimToEmpty(batchEmail.getExternalEmailSource()))
		            .addValue("CREATETIME", batchEmail.getExternalEmailID());
		
		jdbcTemplate.update(updateBatchByxternalSourceAndEmail, mapSqlParameterSource);
		
	}

	public List<BatchEmailDTO> getBodyListForHdr(String externalEmailSource, int externalEmailID) {
		
		return jdbcTemplate.query(selectBatchByxternalSourceAndEmail, new MapSqlParameterSource()
				.addValue("extemlsrc", externalEmailSource).addValue("extemlid", externalEmailID), new BatchXternalSourceEmailRowMapper());
	}

}
