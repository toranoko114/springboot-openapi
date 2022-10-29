package com.example.todoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthApi {

  /**
   * Ambiguous mapping. Cannot map 'healthController' bean method
   * 他のマッピングと被っている際に発生する
   * @see 'http://blog.livedoor.jp/pg_spr/archives/1027055856.html'
   *
   * OpenAPI generator でSpringフォルダを生成する際にControllerを生成しないようにする
   *
   */


  @Override
  public ResponseEntity<Void> healthGet() {
    return ResponseEntity.ok().build();
  }

}
