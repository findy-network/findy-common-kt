package org.findy_network.findy_common_kt

import io.grpc.CallCredentials.*
import io.grpc.ManagedChannel
import org.findy_network.findy_common_kt.ProtocolServiceGrpcKt.ProtocolServiceCoroutineStub

class ProtocolClient(private val channel: ManagedChannel, private val token: String) {
  private val stub: ProtocolServiceCoroutineStub =
      ProtocolServiceCoroutineStub(channel).withCallCredentials(Creds(token = token))

  suspend fun sendMessage(connectionId: String, message: String): ProtocolID {
    val response =
        stub.start(
            Protocol.newBuilder()
                .setTypeID(Protocol.Type.BASIC_MESSAGE)
                .setRole(Protocol.Role.INITIATOR)
                .setConnectionID(connectionId)
                .setBasicMessage(Protocol.BasicMessageMsg.newBuilder().setContent(message))
                .build())

    return response
  }
}
