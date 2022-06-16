package ru.vekhov.spr.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vekhov.spr.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO public.\"Person\" VALUES (?, ?, " + createId() + ", ?)",
                person.getAge(), person.getName(), person.getEmail());
    }

    private static int peopleCount;

    public List<Person> index(){

        return jdbcTemplate.query("SELECT * FROM public.\"Person\"", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(final int id) {

        return jdbcTemplate.query("SELECT * FROM public.\"Person\" WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    private int createId() {
        return peopleCount++;
    }

    public void update(int id, Person updatedPerson) {

        jdbcTemplate.update("UPDATE public.\"Person\" SET age=?, name=?, email=? WHERE id=?",
                updatedPerson.getAge(), updatedPerson.getName(), updatedPerson.getEmail(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM public.\"Person\" WHERE id=?", id);

    }
}
