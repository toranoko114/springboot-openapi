package com.example.todoapi.controller.sample;

import com.example.todoapi.service.sample.SampleService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleController {

  private final SampleService service;

  @GetMapping
  public SampleDTO index() {
    var entity = service.find();
    return SampleDTO.builder().content(entity.getContent()).timestamp(LocalDateTime.now()).build();
  }

}
