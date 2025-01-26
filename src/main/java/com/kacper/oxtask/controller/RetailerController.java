package com.kacper.oxtask.controller;

import com.kacper.oxtask.domain.Retailer;
import com.kacper.oxtask.service.ManufacturerService;
import com.kacper.oxtask.service.RetailerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController()
@RequestMapping("/api/retailer")
@RequiredArgsConstructor
@Slf4j
public class RetailerController {

  private final RetailerService retailerService;

  @PostMapping("/{name}")
  public ResponseEntity<?> insert(@PathVariable("name") String name)
  {
    try {
      return ResponseEntity.ok(retailerService.insert(name));
    } catch (DuplicateKeyException ex) {
      log.error("Retailer already exists with name: {}", name, ex);
      return ResponseEntity.badRequest().body("Retailer " + name + " already exists.");
    } catch (Exception ex) {
      log.error("Error inserting retailer with name: {}", name, ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }

  @GetMapping("/{name}")
  public ResponseEntity<?> checkRetailerExists(@PathVariable("name") String name)
  {
    try {
      return ResponseEntity.ok(retailerService.checkIfRetailerExists(name));
    } catch (Exception ex) {
      log.error("Error fetching retailer with name: {}", name, ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }
}
