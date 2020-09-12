package com.yunwuye.sample.common.base.annotation;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldCNNameHandler {
  private static final Logger logger = LoggerFactory.getLogger(FieldCNNameHandler.class);

  public Object resolveFieldName(Object object) {
    for (Field field : object.getClass().getDeclaredFields()) {
      FieldCNName fieldCNName = field.getAnnotation(FieldCNName.class);
      if (fieldCNName != null) {
        logger.info("字段[{0}]中文释义为：[{1}]", field.getName(), fieldCNName.value());
      }
    }
    return object;
  }
}
