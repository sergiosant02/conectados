package com.sergiosantiago.conectados.services.base;

import java.util.List;

public interface BaseService<T, ID> {

    public T save(T entity);

    public T update(T entity);

    public void delete(T entity);

    public void deleteById(ID id);

    public T findById(ID id);

    public List<T> findAll();

}
