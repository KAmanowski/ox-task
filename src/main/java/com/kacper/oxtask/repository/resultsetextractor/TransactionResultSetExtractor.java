package com.kacper.oxtask.repository.resultsetextractor;

import com.kacper.oxtask.domain.transaction.Transaction;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionResultSetExtractor implements ResultSetExtractor<List<Transaction>> {


  @Override
  public List<Transaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Transaction> polls = new ArrayList<>();

    while (rs.next()) {
      polls.add(Transaction.builder()
          .transactionId(UUID.fromString(rs.getString("transaction_id")))
          .retailer(rs.getString("retailer_id"))
          .manufacturer(rs.getString("manufacturer_id"))
          .productCode(rs.getString("product_code"))
          .transactionDate(new DateTime(rs.getTimestamp("transaction_date")))
          .quantity(rs.getFloat("quantity"))
          .value(rs.getFloat("value"))
          .build());
    }

    return polls;
  }
}
