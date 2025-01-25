package com.kacper.oxtask.repository.implementation;

import com.kacper.oxtask.repository.IRetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RetailerRepository implements IRetailerRepository {

  private final static String INSERT_RECORD = ""
      + "INSERT INTO public.transaction (manufacturer";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


  @Override
  public UUID insertRecord() {
    return null;
  }
}
