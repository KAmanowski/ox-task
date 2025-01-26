package com.kacper.oxtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kacper.oxtask.domain.transaction.TransactionSearchQuery;
import com.kacper.oxtask.domain.transaction.Transaction;
import com.kacper.oxtask.service.ManufacturerService;
import com.kacper.oxtask.service.RetailerService;
import com.kacper.oxtask.service.TransactionService;
import com.kacper.oxtask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController()
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

  private final TransactionService transactionService;
  private final ManufacturerService manufacturerService;
  private final RetailerService retailerService;
  private final UserService userService;
  private final ObjectMapper objectMapper;

  @PostMapping()
  public ResponseEntity<?> insert(@RequestBody Transaction transaction) throws JsonProcessingException {
    try {
      if (StringUtils.isAllBlank(transaction.getManufacturer())
          || !manufacturerService.checkIfManufacturerExists(transaction.getManufacturer())) {
        return ResponseEntity.badRequest().body("Manufacturer is empty or doesn't exist.");
      }

      if (StringUtils.isAllBlank(transaction.getRetailer())
          || !retailerService.checkIfRetailerExists(transaction.getRetailer())) {
        return ResponseEntity.badRequest().body("Retailer is empty or doesn't exist.");
      }

      // TODO: Validate everything else - probably in some validation class

      return ResponseEntity.ok(transactionService.insert(transaction));
    } catch (Exception ex) {
      log.error("Error inserting transaction {}", objectMapper.writeValueAsString(transaction), ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllTransactions() {
    try {
      // I'd paginate this or something (with pageSize, pageNumber etc.) but for brevity we're gonna be fetching all
      return ResponseEntity.ok(transactionService.fetchAllTransactions());
    } catch (Exception ex) {
      log.error("Error fetching all transactions.", ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }

  @PostMapping("/search")
  public ResponseEntity<?> searchAllTransactionsQuantity(@RequestBody TransactionSearchQuery transactionSearchQuery)
      throws JsonProcessingException {
    try {
      if (StringUtils.isAllEmpty(transactionSearchQuery.getSum())
          || (!transactionSearchQuery.getSum().equalsIgnoreCase("quantity")
          && !transactionSearchQuery.getSum().equalsIgnoreCase("value"))) {
        return ResponseEntity.badRequest().body("'sum' is invalid.");
      }

      return ResponseEntity.ok(transactionService.searchTransactions(transactionSearchQuery));
    } catch (Exception ex) {
      log.error("Error searching for transactions with parameters: {}",
          objectMapper.writeValueAsString(transactionSearchQuery), ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }

  @DeleteMapping("/all")
  public ResponseEntity<?> deleteAllTransactions(@RequestHeader("username") String username,
                                                 @RequestHeader("token") String token) {
    try {
      if (!userService.checkCredentials(username, token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      transactionService.deleteAll();
      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      log.error("Error deleting all transactions.", ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }
}
