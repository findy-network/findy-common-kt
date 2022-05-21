package org.findy_network.findy_common_kt.sample

import org.findy_network.findy_common_kt.Connection
import org.findy_network.findy_common_kt.Invitation
//import io.grpc.ManagedChannel
//import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class Sample() {
}


suspend fun main() {
    val testAuthUrl = System.getenv("AUTH_URL") ?: "http://localhost:8888"
    val testAuthOrigin = System.getenv("AUTH_ORIGIN") ?: "http://localhost:8888"
    val testUserName = System.getenv("USER_NAME") ?: "user-" + System.currentTimeMillis()
    val testSeed = System.getenv("SEED") ?: ""
    val testKey = System.getenv("KEY") ?: "15308490f1e4026284594dd08d31291bc8ef2aeac730d0daf6ff87bb92d4336c"
    val server = System.getenv("SERVER") ?: "localhost"
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val connection: Connection = Connection(
        authUrl = testAuthUrl,
        authOrigin = testAuthOrigin,
        userName = testUserName,
        seed = testSeed,
        key = testKey,
        server = server,
        port = port
    )
    println(connection.toString())

    val invitation: Invitation = connection.agentClient.createInvitation(label = "test")



}
