package com.bearcave.passageplanning.base.database.withcustomkey;

import java.util.List;

public interface CRUDWithCustomKey<T, Key> {
    /**
     * @param element
     * @return the row ID of the newly inserted row
     */
    long insert(T element);
    
    T read(Key id);
    List<T> readAll();


    /**
     * @param element
     * @return the number of rows affected
     */
    int update(T element);


    /**
     * @param element
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     */
    int delete(T element);
}
