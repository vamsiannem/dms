/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Repository(value = "commonRepository")
@Transactional
public class CommonRepository {

	@Resource
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Criteria createCriteria(Class clazz) {
		return getSession().createCriteria(clazz);
	}

	public Query createQuery(String queryString) {
		return getSession().createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return getSession().createSQLQuery(queryString);
	}

	public <T> Serializable save(T entity) {
		return getSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	public <T> void update(T entity) {
		getSession().update(entity);
	}

	public <T> void delete(T entity) {
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> clazz, Object... nameValues) {
		Criteria criteria = findMatchingObjects(clazz, nameValues);
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T> T load(Class<T> clazz, Serializable id) {
		return (T) getSession().load(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz, Object... nameValues) {
		return findMatchingObjects(clazz, nameValues).list();
	}

	public <T> List<T> findAllInAsc(Class<T> clazz, String orderByProperty) {
		return findAllWithOrderBy(clazz, orderByProperty, true);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAllWithOrderBy(Class<T> clazz, String orderByProperty, boolean isAscending, Object... nameValues) {
		Criteria criteria = findMatchingObjects(clazz, nameValues);
		criteria.addOrder(isAscending ? Order.asc(orderByProperty) : Order.desc(orderByProperty));
		return criteria.list();
	}

	private <T> Criteria findMatchingObjects(Class<T> clazz, Object[] nameValues) {
		if (nameValues.length % 2 == 1) {
			throw new IllegalArgumentException("Parameters should contain name value sequentially.");
		}

		Criteria criteria = getSession().createCriteria(clazz);
		for (int i = 0; i < nameValues.length; i = i + 2) {
			criteria.add(Restrictions.eq((String) nameValues[i], nameValues[i + 1]));
		}
		return criteria;
	}
}
