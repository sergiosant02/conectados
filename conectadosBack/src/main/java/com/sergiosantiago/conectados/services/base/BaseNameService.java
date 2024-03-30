package com.sergiosantiago.conectados.services.base;

import java.util.List;

public interface BaseNameService<T, ID> extends BaseService<T, ID> {

    public List<T> findByName(String name);

    public List<T> findLikeByName(String name);

}
