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
public class BatchXternalSourceEmailRowMapper implements RowMapper<BatchEmailDTO> {
	private static final Logger log = LoggerFactory.getLogger(BatchXternalSourceEmailRowMapper.class);

	@Override
	public BatchEmailDTO mapRow(ResultSet rs, int i) throws SQLException {
		
		BatchEmailDTO batchEmail = new BatchEmailDTO();
		batchEmail.setExternalEmailSource(StringUtils.trimToEmpty(rs.getString("extemlsrc")));
		batchEmail.setExternalEmailID(rs.getInt("extemlid"));
		batchEmail.setSequence(rs.getInt("seq"));
		if(StringUtils.isNotBlank(rs.getString("emailtxt"))) {
			batchEmail.setEmailText(rs.getString("emailtxt"));
		} else {
			batchEmail.setEmailText("");
		}
		batchEmail.setParagraphFlag(rs.getInt("pgphflg"));
		batchEmail.setExternalCode(StringUtils.trimToEmpty(rs.getString("extcod")));
		
		return batchEmail;
	}
}
