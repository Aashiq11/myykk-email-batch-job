package com.myykk.dao;

import com.myykk.model.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmailRowMapper implements RowMapper<EmailDTO> {
	private static final Logger log = LoggerFactory.getLogger(EmailRowMapper.class);

	@Override
	public EmailDTO mapRow(ResultSet resultSet, int i) throws SQLException {

		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setEmailid(resultSet.getString("emailid").trim());
		emailDTO.setCustnum(resultSet.getString("custnum").trim());
		emailDTO.setDocnum(resultSet.getString("docnum").trim());
		emailDTO.setLangflg(resultSet.getString("langflg").trim());
		emailDTO.setFrmemail(resultSet.getString("frmemail").trim());
		emailDTO.setCreatetime(resultSet.getString("createtime").trim());
		
		return emailDTO;
	}
}
