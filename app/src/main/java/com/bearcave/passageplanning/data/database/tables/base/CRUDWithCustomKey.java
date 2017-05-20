package com.bearcave.passageplanning.data.database.tables.base;

import java.util.List;

public interface CRUDWithCustomKey<Element, Key> {
    /**
     * @param element
     * @return the row ID of the newly inserted row
     */
    Key insert(Element element);
    
    Element read(Key id);
    List<Element> readAll();


    /**
     * @param element
     * @return the number of rows affected
     */
    int update(Element element);


    /**
     * @param element
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     */
    int delete(Element element);
}
