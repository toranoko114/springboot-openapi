package com.example.todoapi.controller.sample;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
public class SampleDTO {

  String content;
  LocalDateTime timestamp;

}
