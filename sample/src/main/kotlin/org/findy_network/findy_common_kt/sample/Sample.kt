package org.findy_network.findy_common_kt.sample

import org.findy_network.findy_common_kt.Connection
import org.findy_network.findy_common_kt.Invitation

class Sample() {}

suspend fun main() {
  val testAuthUrl = System.getenv("AUTH_URL") ?: "http://localhost:8888"
  val testAuthOrigin = System.getenv("AUTH_ORIGIN") ?: "http://localhost:8888"
  val testUserName = System.getenv("USER_NAME") ?: "user-" + System.currentTimeMillis()
  val testSeed = System.getenv("SEED") ?: ""
  val testKey =
      System.getenv("KEY") ?: "15308490f1e4026284594dd08d31291bc8ef2aeac730d0daf6ff87bb92d4336c"
  val server = System.getenv("SERVER") ?: "localhost"
  val port = System.getenv("PORT")?.toInt() ?: 50051
  val certPath = System.getenv("CERT_PATH") ?: null

  val connection: Connection =
      Connection(
          authUrl = testAuthUrl,
          authOrigin = testAuthOrigin,
          userName = testUserName,
          seed = testSeed,
          key = testKey,
          server = server,
          port = port,
          certFolderPath = certPath)
  println(connection.toString())

  val invitation: Invitation = connection.agentClient.createInvitation(label = "test")
}
