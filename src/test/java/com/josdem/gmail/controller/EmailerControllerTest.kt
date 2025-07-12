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
import com.google.api.client.auth.oauth2.TokenResponseException
import com.josdem.gmail.model.MessageCommand
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class EmailerControllerTest {
    @Autowired private lateinit var mockMvc: MockMvc

    @Autowired private lateinit var objectMapper: ObjectMapper

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
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = "true")
    fun `should not send email due to invalid credentials`(testInfo: TestInfo) {
        log.info(testInfo.displayName)

        val validMessage = message
        validMessage.token = "userToken"

        val request =
            post("/emailer/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message))

        assertThrows<TokenResponseException> {
            mockMvc
                .perform(request)
                .andExpect(status().isUnauthorized)
        }
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
