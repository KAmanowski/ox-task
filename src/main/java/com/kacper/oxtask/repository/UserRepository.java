package com.kacper.oxtask.repository;

import com.kacper.oxtask.exception.NotFoundRepositoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final static String INSERT_USER = ""
      + "INSERT INTO public.user (name, hash) VALUES (:name, :hash)";

  private final static String FETCH_HASH = ""
      + "SELECT hash FROM public.user WHERE name = :name";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public String fetchHash(String name) {
    try {
      MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
      sqlParameterSource.addValue("name", name);

      byte[] bytes = namedParameterJdbcTemplate.queryForObject(FETCH_HASH, sqlParameterSource, byte[].class);

      return new String(bytes, StandardCharsets.UTF_8);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundRepositoryException("User not found for name: " + name, ex);
    }
  }

  public void insert(String name, String hash) {
    MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("name", name);
    sqlParameterSource.addValue("hash", hash.getBytes());

    namedParameterJdbcTemplate.update(INSERT_USER, sqlParameterSource);
  }
}
