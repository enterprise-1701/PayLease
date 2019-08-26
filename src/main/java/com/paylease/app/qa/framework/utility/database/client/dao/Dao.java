package com.paylease.app.qa.framework.utility.database.client.dao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Interface for Data Access Objects.
 *
 * @author Jeffrey Walker
 */
public interface Dao<T> {

  boolean create(Connection connection, T t);

  ArrayList<T> findById(Connection connection, long id, int limit);

  int update(Connection connection, T t);

  boolean delete(Connection connection, T t);
}
