package com.example.service_ticket.repository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    List<T> findAll();

    T findById(ID id);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);

    void save(T entity);
}
