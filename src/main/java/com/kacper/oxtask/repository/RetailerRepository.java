package com.kacper.oxtask.repository;

import com.kacper.oxtask.exception.NotFoundRepositoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.kacper.oxtask.config.CacheConfig.*;

@Repository
@RequiredArgsConstructor
public class RetailerRepository {

  private final static String INSERT_RETAILER = ""
      + "INSERT INTO public.retailer (name) VALUES (:name)";

  private final static String CHECK_RETAILER_EXISTS = ""
      + "SELECT 1 FROM public.retailer WHERE name = :name";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Cacheable(RETAILER_EXISTS)
  public Boolean checkIfRetailerExists(String name) {
    try {
      MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
      sqlParameterSource.addValue("name", name);

      return namedParameterJdbcTemplate.queryForObject(CHECK_RETAILER_EXISTS, sqlParameterSource, Boolean.class);
    } catch (EmptyResultDataAccessException ex) {
      return false;
    }
  }

  public void insert(String name) {
    MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("name", name);

    namedParameterJdbcTemplate.update(INSERT_RETAILER, sqlParameterSource);
  }
}
