package com.kacper.oxtask.repository;

import com.kacper.oxtask.exception.NotFoundRepositoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.kacper.oxtask.config.CacheConfig.MANUFACTURER_EXISTS;

@Repository
@RequiredArgsConstructor
public class ManufacturerRepository {

  private final static String INSERT_MANUFACTURER = ""
      + "INSERT INTO public.manufacturer (name) VALUES (:name)";

  private final static String CHECK_MANUFACTURER_EXISTS = ""
      + "SELECT 1 FROM public.manufacturer WHERE name = :name";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Cacheable(MANUFACTURER_EXISTS)
  public Boolean checkIfManufacturerExists(String name) {
    try {
      MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
      sqlParameterSource.addValue("name", name);

      return namedParameterJdbcTemplate.queryForObject(CHECK_MANUFACTURER_EXISTS, sqlParameterSource, Boolean.class);
    } catch (EmptyResultDataAccessException ex) {
      return false;
    }
  }

  public UUID insert(String name) {
    MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("name", name);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(INSERT_MANUFACTURER, sqlParameterSource, keyHolder);

    return UUID.fromString(String.valueOf(keyHolder.getKeyList().get(0).get("id")));
  }
}
