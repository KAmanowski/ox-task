package com.kacper.oxtask.repository.resultsetextractor;

import com.kacper.oxtask.domain.transaction.Transaction;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class TransactionSearchResultSetExtractor implements ResultSetExtractor<Map<String, Float>> {


  @Override
  public Map<String, Float> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Map<String, Float> map = new HashMap<>();

    while (rs.next()) {
      String productCode = rs.getString("product_code");
      Float total = rs.getFloat("sum_total");
      if (map.containsKey(productCode)) {
        map.put(productCode, map.get(productCode) + total);
      } else {
        map.put(rs.getString("product_code"), rs.getFloat("sum_total"));
      }
    }

    return map;
  }
}
