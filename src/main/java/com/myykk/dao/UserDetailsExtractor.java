package com.myykk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.myykk.model.UserDetailsDTO;

@Component
public class UserDetailsExtractor implements ResultSetExtractor<UserDetailsDTO> {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsExtractor.class);

	@Override
	public UserDetailsDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		
		UserDetailsDTO userDetails = new UserDetailsDTO();
		if (resultSet.next()) {			 
			  
			  userDetails.setEmailId(resultSet.getString("emailid"));
			  userDetails.setFirstName(resultSet.getString("firstname"));
			  userDetails.setLastName(resultSet.getString("lastname"));
			  userDetails.setPassword(resultSet.getString("passwd"));
			  userDetails.setHint_question(resultSet.getString("hint_quest"));
			  userDetails.setHint_answer(resultSet.getString("hint_ans"));
			   		  
			  
		}
		
		return userDetails;
		 
	}

	
	 
	 
}
