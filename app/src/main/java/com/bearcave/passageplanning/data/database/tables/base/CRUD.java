package com.bearcave.passageplanning.data.database.tables.base;

import java.util.List;

public interface CRUD<Element> {
    long create(Element element);
    Element read(long id);
    List<Element> readAll();
    int update(Element element);
    int delete(Element element);
}
