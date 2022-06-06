package org.findy_network.findy_common_kt.sample

import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import org.findy_network.findy_common_kt.Connection
import org.findy_network.findy_common_kt.Invitation
import org.findy_network.findy_common_kt.Notification
import org.findy_network.findy_common_kt.Protocol

class Sample() {}

suspend fun main() {
  val testAuthUrl = System.getenv("AUTH_URL") ?: "http://localhost:8088"
  val testAuthOrigin = System.getenv("AUTH_ORIGIN") ?: "http://localhost:3000"
  val testUserName = System.getenv("USER_NAME") ?: "user-" + System.currentTimeMillis()
  val testSeed = System.getenv("SEED") ?: ""
  val testKey =
      System.getenv("KEY") ?: "15308490f1e4026284594dd08d31291bc8ef2aeac730d0daf6ff87bb92d4336c"
  val server = System.getenv("SERVER") ?: "localhost"
  val port = System.getenv("PORT")?.toInt() ?: 50052
  val certPath = System.getenv("CERT_PATH") ?: "../tools/local-test-cert"

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

  // Create connection invitation
  val invitation: Invitation = connection.agentClient.createInvitation(label = "test")
  println("\n\n**********************")
  println("Read invitation using another aries agent:")
  println(invitation.getJSON())
  println("**********************\n\n")

  try {
    // Start listening status notifications
    connection.agentClient.listen().collect {
      println("Received from Agency:\n$it")
      val status = it.notification
      when (status.typeID) {
        Notification.Type.STATUS_UPDATE -> {
          when (status.protocolType) {

            // New connection established -> send greeting
            Protocol.Type.DIDEXCHANGE -> {
              val message = "Hello World!"
              connection.protocolClient.sendMessage(status.connectionID, message)
            }

            // Message sent -> all ready, cancel streaming
            Protocol.Type.BASIC_MESSAGE -> {
              currentCoroutineContext()[Job]?.cancel()
            }
            else -> println("no handler for protocol type: ${status.protocolType}")
          }
        }
        else -> println("no handler for notification type: ${status.typeID}")
      }
    }
  } catch (e: Exception) {
    println(e.toString())
  }
}
