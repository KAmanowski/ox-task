package com.kacper.oxtask.controller;

import com.kacper.oxtask.domain.Manufacturer;
import com.kacper.oxtask.domain.Retailer;
import com.kacper.oxtask.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController()
@RequestMapping("/api/manufacturer")
@RequiredArgsConstructor
@Slf4j
public class ManufacturerController {

  private final ManufacturerService manufacturerService;

  @PostMapping("/{name}")
  public ResponseEntity<?> insert(@PathVariable("name") String name)
  {
    try {
      return ResponseEntity.ok(manufacturerService.insert(name));
    } catch (DuplicateKeyException ex) {
      log.error("Manufacturer already exists with name: {}", name, ex);
      return ResponseEntity.badRequest().body("Manufacturer " + name + " already exists.");
    } catch (Exception ex) {
      log.error("Error inserting manufacturer with name: {}", name, ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }

  @GetMapping("/{name}")
  public ResponseEntity<?> checkManufacturerExists(@PathVariable("name") String name)
  {
    try {
      return ResponseEntity.ok(manufacturerService.checkIfManufacturerExists(name));
    } catch (Exception ex) {
      log.error("Error fetching manufacturer with name: {}", name, ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }
}
