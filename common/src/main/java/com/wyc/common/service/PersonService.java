package com.wyc.common.service;

import com.wyc.common.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class PersonService extends BaseAbstractService<Person>{

    @Autowired
    private DataSource dataSource;

    private String database = "mysql";
    @Override
    protected DataSource dataSource() {
        return dataSource;
    }

    @Override
    protected String database() {
        return database;
    }
}
