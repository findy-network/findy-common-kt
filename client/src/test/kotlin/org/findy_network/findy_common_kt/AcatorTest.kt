package org.findy_network.findy_common_kt

import kotlin.test.Test
import kotlin.test.assertEquals

internal class MockExecutor : Executor {
  val testToken =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1biI6IlJNQWlRZWJ5Z2FNc256U2NSM0R5ZjYiLCJsYWJlbCI6InVzZXItMTY1Mjk1MDk3MDgyNiIsImV4cCI6MTY1MzIxMDE3Mn0.QO5lwjXNELVuTMomBjpfeTVB_HWGiQlxmGWUtsvUD5Y"
  override fun run(command: String): String? {
    if (command.contains("--version")) return "findy-agent-cli version 0.24.28"
    else if (command.contains("register")) return "\"Registration Success\""
    else if (command.contains("login")) return testToken
    return ""
  }
}

internal class AcatorTest {

  private val testAuthUrl = "https://auth.example.com"
  private val testAuthOrigin = "https://auth.example.com"
  private val testUserName = "user-" + System.currentTimeMillis()
  private val testSeed = ""
  private val testKey = "15308490f1e4026284594dd08d31291bc8ef2aeac730d0daf6ff87bb92d4336c"
  private val mockExec = MockExecutor()
  private val testAcator: Acator =
      Acator(
          authUrl = testAuthUrl,
          authOrigin = testAuthOrigin,
          userName = testUserName,
          seed = testSeed,
          key = testKey,
          exec = mockExec)

  @Test
  fun testCreate() {
    assertEquals(mockExec.testToken, testAcator.login())
  }
}
