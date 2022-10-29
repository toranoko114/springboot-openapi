package com.example.todoapi.controller.advice;

import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.InvalidParam;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BadRequestErrorGenerator {


  /**
   * MethodArgumentNotValidException発生時のレスポンスを生成.
   *
   * @return BadRequestError
   */
  public static BadRequestError generate(MethodArgumentNotValidException ex) {
    var params = ex.getFieldErrors().stream().map(BadRequestErrorGenerator::generateInvalidParam)
        .collect(Collectors.toList());

    var error = new BadRequestError();
    error.setInvalidParams(params);
    return error;
  }

  /**
   * ConstraintViolationException発生時のレスポンスを生成.
   *
   * @return BadRequestError
   */
  public static BadRequestError generate(ConstraintViolationException ex) {
    var invalidParamList = generateInvalidParamList(ex);
    var error = new BadRequestError();
    error.setInvalidParams(invalidParamList);
    return error;
  }

  private static List<InvalidParam> generateInvalidParamList(ConstraintViolationException ex) {
    return ex.getConstraintViolations().stream().map(
        BadRequestErrorGenerator::generateInvalidParam).collect(Collectors.toList());
  }

  private static InvalidParam generateInvalidParam(ConstraintViolation<?> it) {
    var parameterOpt = StreamSupport.stream(it.getPropertyPath().spliterator(), true)
        .filter(node -> node.getKind().equals(ElementKind.PARAMETER)).findFirst();
    var invalidParam = new InvalidParam();
    parameterOpt.ifPresent(p -> invalidParam.setName(p.getName()));
    invalidParam.setReason(it.getMessage());
    return invalidParam;
  }

  private static InvalidParam generateInvalidParam(FieldError field) {
    var param = new InvalidParam();
    param.setName(field.getField());
    param.setReason(field.getDefaultMessage());
    return param;
  }
}
