package org.springframework.samples.IdusMartii.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
public interface MatchRepository extends CrudRepository<Match, String> {
	/**
	 * Save a <code>Match</code> to the data store, either inserting or updating it.
	 * @param Match the <code>Match</code> to save
	 * @return 
	 * @return 
	 * @see BaseEntity#isNew
	 */
}
