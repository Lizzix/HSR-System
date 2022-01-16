package com.esoe.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
abstract public class DAO<T> {

    protected JdbcTemplate jdbcTemplate;
    public DAO() {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans-config.xml");
        jdbcTemplate = new JdbcTemplate(context.getBean(DataSource.class));
        if (jdbcTemplate == null) {
            System.out.println("jdbcTemplate is null");
        }
    }

    abstract List<T> list();
    abstract void create(T t);
    abstract Optional<T> get(int id);
    abstract void update(T t, int id);
    abstract void delete(int id);
}
