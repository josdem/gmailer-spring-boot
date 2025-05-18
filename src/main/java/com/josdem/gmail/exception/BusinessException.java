/*
  Copyright 2025 Jose Morales contact@josdem.io

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.josdem.gmail.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusinessException extends RuntimeException {
  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public BusinessException(String message) {
    super(message);
    log.error(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
    log.error(message, cause);
  }
}
