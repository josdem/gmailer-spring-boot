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

package com.josdem.gmail.mail

import com.josdem.gmail.helper.InternetAddressHelper
import com.josdem.gmail.helper.MimeMessageHelper
import com.josdem.gmail.helper.SessionHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

internal class EmailCreatorTest {
    @Mock
    private lateinit var properties: Properties

    @Mock
    private lateinit var sessionHelper: SessionHelper

    @Mock
    private lateinit var mimeMessageHelper: MimeMessageHelper

    @Mock
    private lateinit var internetAddressHelper: InternetAddressHelper

    @Mock
    private lateinit var session: Session

    @Mock
    private lateinit var mimeMessage: MimeMessage

    @Mock
    private lateinit var fromInternetAddress: InternetAddress

    @Mock
    private lateinit var toInternetAddress: InternetAddress

    @InjectMocks
    private lateinit var emailCreator: EmailCreator

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `should create mime message`(testInfo: TestInfo) {
        log.info(testInfo.displayName)

        val fromEmailAddress = "vetlog@josdem.io"
        val toEmailAddress = "contact@josdem.io"
        val subject = "Test Subject"
        val body = "Test Body"

        `when`(sessionHelper.createSession(properties)).thenReturn(session)
        `when`(mimeMessageHelper.createMimeMessage(session)).thenReturn(mimeMessage)
        `when`(internetAddressHelper.createInternetAddress(fromEmailAddress)).thenReturn(fromInternetAddress)
        `when`(internetAddressHelper.createInternetAddress(toEmailAddress)).thenReturn(toInternetAddress)

        emailCreator.create(toEmailAddress, fromEmailAddress, subject, body)

        verify(mimeMessage).setFrom(fromInternetAddress)
        verify(mimeMessage).addRecipient(MimeMessage.RecipientType.TO, toInternetAddress)
        verify(mimeMessage).subject = subject
        verify(mimeMessage).setText(body)
    }
}
