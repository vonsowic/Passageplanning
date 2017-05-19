package com.bearcave.passageplanning.main_activity;

import java.util.List;

public interface CRUD<Element> {
    long create(Element element);
    Element read(long id);
    List<Element> readAll();
    int update(Element element);
    int delete(Element element);
}
