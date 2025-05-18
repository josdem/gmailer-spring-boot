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
/*
 * Annotation source - https://medium.com/javarevisited/generate-rest-api-documentation-for-spring-boot-project-using-swagger-996e48fe0cea
 * - Tim
 */

package com.josdem.gmail.controller;

import com.josdem.gmail.model.MessageCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/")
@Tag(name = "Root Controller", description = "Manages views for root page")
public class IndexController {
  // TODO: Give more descriptive summaries or change them if necessary
  @Value("${token}")
  private String token;

  @Operation(summary = "Returns the root page")
  @GetMapping("/")
  public String index() {
    log.info("Calling index");
    return "index";
  }

  @Operation(summary = "Returns the command line page")
  @GetMapping("/command-line")
  public String commandLine() {
    log.info("Calling command line");
    return "command_line";
  }

  @Operation(summary = "Returns the form page")
  @GetMapping("/form")
  public ModelAndView form() {
    log.info("Calling form");
    var modelAndView = new ModelAndView("form");
    var message = new MessageCommand();
    message.setToken(token);
    modelAndView.addObject("message", message);
    return modelAndView;
  }

  @Operation(summary = "Returns the batch page")
  @GetMapping("/batch")
  public String batch() {
    log.info("Calling batch");
    return "batch";
  }

  @Operation(summary = "Returns the contact page")
  @GetMapping("/contact")
  public ModelAndView contact() {
    log.info("Calling contact");
    var modelAndView = new ModelAndView("contact");
    var message = new MessageCommand();
    message.setToken(token);
    modelAndView.addObject("message", message);
    return modelAndView;
  }

  @Operation(summary = "Returns the flyer page")
  @GetMapping("/flyer")
  public String flyer() {
    log.info("Calling flyer");
    return "flyer";
  }
}
