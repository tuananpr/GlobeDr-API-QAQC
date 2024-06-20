package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;

import java.util.List;

public interface IDAO<E, T, K>{
    E get(K i);
    List<E> getAll();
    void add(E e);
    void update (E e);
    T delete(K e);
    SqlDB getDB();
}

