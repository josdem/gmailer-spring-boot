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

package com.josdem.gmail.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.josdem.gmail.exception.BusinessException
import com.josdem.gmail.model.MessageCommand
import com.josdem.gmail.service.EmailService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class EmailerControllerTest {
    @Autowired private lateinit var mockMvc: MockMvc

    @Autowired private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var emailService: EmailService

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `should not send email due to wrong token`(testInfo: TestInfo) {
        log.info(testInfo.displayName)

        val request =
            post("/emailer/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message))
        mockMvc
            .perform(request)
            .andExpect(status().isForbidden)
    }

    @Test
    fun `should not send email due to invalid credentials`(testInfo: TestInfo) {
        log.info(testInfo.displayName)

        val validMessage = message
        // Use the token from system properties if available, otherwise use test config
        val testToken = System.getProperty("token") ?: "test-token-for-unit-tests"
        validMessage.token = testToken

        // Mock the email service to throw BusinessException
        doThrow(BusinessException("Invalid Gmail credentials"))
            .whenever(emailService)
            .sendEmail(validMessage)

        val request =
            post("/emailer/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validMessage))

        // When the email service throws BusinessException, the controller should return 401
        mockMvc
            .perform(request)
            .andExpect(status().isUnauthorized)
    }

    private val message =
        MessageCommand().apply {
            name = "josdem"
            email = "contact@josdem.io"
            subject = "Hello from Junit5"
            message = "This is a test message"
            template = "message.ftl"
            token = "invalidToken"
        }
}
