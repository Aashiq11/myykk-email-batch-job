package com.myykk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.myykk.model.IssueDTO;

@Component
public class IssueExtractor implements ResultSetExtractor<IssueDTO> {
	private static final Logger log = LoggerFactory.getLogger(IssueExtractor.class);

	@Override
	public IssueDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		
		IssueDTO issue = new IssueDTO();
		if (resultSet.next()) {	
			
			issue.setCatgory(resultSet.getString("iscatgory"));
			issue.setDetail(resultSet.getString("isdetail"));
			issue.setPrvstatus(resultSet.getString("prvstatus"));
			
		}
		
		return issue;
		 
	}	 
	 
}
