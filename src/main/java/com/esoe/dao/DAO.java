package com.esoe.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class DAO<T> {

    protected JdbcTemplate jdbcTemplate;

    protected DAO() {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans-config.xml");
        jdbcTemplate = new JdbcTemplate(context.getBean(DataSource.class));
    }

    abstract List<T> list();
    abstract int create(T t);
    abstract Optional<T> get(int id);
    abstract int update(T t, int id);
    abstract int delete(int id);
}
