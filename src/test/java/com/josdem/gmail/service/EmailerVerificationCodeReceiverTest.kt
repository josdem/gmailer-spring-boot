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

package com.josdem.gmail.service

import com.josdem.gmail.config.ApplicationProperties
import com.josdem.gmail.service.impl.EmailerVerificationCodeReceiverImpl
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class EmailerVerificationCodeReceiverTest {
    @Mock
    private lateinit var applicationProperties: ApplicationProperties

    @InjectMocks
    private lateinit var emailerVerificationCodeReceiver: EmailerVerificationCodeReceiverImpl

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get redirect URL`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val expectedUrl = "https://josdem.io/redirect"
        `when`(applicationProperties.callbackUrl).thenReturn(expectedUrl)
        assertEquals(expectedUrl, emailerVerificationCodeReceiver.redirectUri)
    }

    @Test
    fun `should get wait for code`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertEquals(StringUtils.EMPTY, emailerVerificationCodeReceiver.waitForCode())
    }
}
