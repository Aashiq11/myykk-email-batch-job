package com.myykk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.myykk.model.BatchEmailDTO;

@Component
public class BatchEmailRowMapper implements RowMapper<BatchEmailDTO> {
	private static final Logger log = LoggerFactory.getLogger(BatchEmailRowMapper.class);

	@Override
	public BatchEmailDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		
		BatchEmailDTO batchEmail = new BatchEmailDTO();
		batchEmail.setExternalEmailSource(StringUtils.trimToEmpty(resultSet.getString("extemlsrc")));
		batchEmail.setExternalEmailID(resultSet.getInt("extemlid"));
		batchEmail.setCcFlag(resultSet.getInt("ccflg"));
		batchEmail.setAttachmentFlag(resultSet.getInt("atchflg"));
		batchEmail.setFrom(StringUtils.trimToEmpty(resultSet.getString("emailfrom")));
		batchEmail.setSendDateTime(resultSet.getTimestamp("snddattim"));
		batchEmail.setSentDateTime(resultSet.getTimestamp("sntdattim"));
		batchEmail.setStatus(resultSet.getInt("emailsts"));
		batchEmail.setSubject(StringUtils.trimToEmpty(resultSet.getString("emailsubj")));
		batchEmail.setTo(StringUtils.trimToEmpty(resultSet.getString("emailto")));
		batchEmail.setExternalCode(StringUtils.trimToEmpty(resultSet.getString("extcod")));
		batchEmail.setBodyType(resultSet.getInt("emlbdytyp"));
		batchEmail.setCampaign(resultSet.getInt("emlcmp"));
		batchEmail.setReleaseNumber(resultSet.getInt("relnbr"));	
		
		return batchEmail;
	}
}
