package xyz.nopalfi.projectbersama.service;

import java.util.List;

public interface DaoService<T> {
    List<T> getAll();
    T save(T t);
    T get(String uuid);
    void update(String uuid, T t);
    void delete(String uuid);
}
