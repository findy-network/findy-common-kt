package org.findy_network.findy_common_kt

import io.grpc.Grpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.TlsChannelCredentials
import java.io.File

class Connection(
    val authUrl: String,
    val authOrigin: String,
    val userName: String,
    val seed: String,
    val key: String,
    val server: String,
    val port: Int,
    val certFolderPath: String? = null,
) {
  val agentClient: AgentClient

  override fun toString(): String {
    return "Connection(authUrl='$authUrl', authOrigin='$authOrigin', userName='$userName', seed='$seed', " +
        "key='$key', server='$server', port=$port, certFolderPath='$certFolderPath')"
  }

  init {
    if (authUrl.isEmpty()) {
      throw IllegalArgumentException("authUrl is empty")
    }
    if (authOrigin.isEmpty()) {
      throw IllegalArgumentException("authOrigin is empty")
    }
    if (userName.isEmpty()) {
      throw IllegalArgumentException("userName is empty")
    }
    if (key.isEmpty()) {
      throw IllegalArgumentException("key is empty")
    }
    if (server.isEmpty()) {
      throw IllegalArgumentException("server is empty")
    }
    if (port < 0) {
      throw IllegalArgumentException("port is negative")
    }
    val acator: Acator =
        Acator(
            authUrl = authUrl, authOrigin = authOrigin, userName = userName, seed = seed, key = key)
    val token = acator.login()

    val channel =
        if (certFolderPath != null) this.getTLSChannelForCertPath()
        else ManagedChannelBuilder.forAddress(server, port).useTransportSecurity().build()

    this.agentClient = AgentClient(channel, token)
  }

  fun getTLSChannelForCertPath(): ManagedChannel {
    val tlsBuilder: TlsChannelCredentials.Builder =
        TlsChannelCredentials.newBuilder().trustManager(File("$certFolderPath/server/server.crt"))

    tlsBuilder.keyManager(
        File("$certFolderPath/client/client.crt"), File("$certFolderPath/client/client.key"))

    val channel =
        Grpc.newChannelBuilderForAddress(server, port, tlsBuilder.build())
            .overrideAuthority("localhost")
            .build()

    return channel
  }
}
