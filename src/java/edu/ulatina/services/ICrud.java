package edu.ulatina.services;

import java.util.List;

/**
 *
 * @author Nwitlyck
 */
public interface ICrud<T> {

    public void insert(T objectTO) throws Exception;

    public void update(T objectTO) throws Exception;

    public void delete(T objectTO) throws Exception;
    
    public void enable(T objectTO) throws Exception;

    public List<T> select(int enable) throws Exception;
}
