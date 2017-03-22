/**
 * 
 */
package com.docusign.envelopes.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Amit.Bist
 *
 */
public abstract class AbstractDAO {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}