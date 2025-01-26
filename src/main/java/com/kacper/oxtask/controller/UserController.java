package com.kacper.oxtask.controller;

import com.kacper.oxtask.service.ManufacturerService;
import com.kacper.oxtask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping("/new")
  public ResponseEntity<?> insert(@RequestParam("name") String name)
  {
    try {
      return ResponseEntity.ok(userService.insert(name));
    } catch (DuplicateKeyException ex) {
      log.error("User already exists with name: {}", name, ex);
      return ResponseEntity.badRequest().body("User " + name + " already exists.");
    } catch (Exception ex) {
      log.error("Error inserting user with name: {}", name, ex);
      return ResponseEntity.internalServerError().body("An error has occurred.");
    }
  }
}
