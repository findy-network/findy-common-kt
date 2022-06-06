package org.findy_network.findy_common_kt

import io.grpc.CallCredentials
import io.grpc.CallCredentials.*
import io.grpc.ManagedChannel
import io.grpc.Metadata
import java.util.UUID
import java.util.concurrent.Executor
import kotlinx.coroutines.flow.Flow
import org.findy_network.findy_common_kt.AgentServiceGrpcKt.AgentServiceCoroutineStub

class Creds(val token: String) : CallCredentials() {

  public override fun applyRequestMetadata(
      requestInfo: RequestInfo,
      appExecutor: Executor,
      applier: MetadataApplier
  ) {
    var headers = Metadata()
    val key: Metadata.Key<String> =
        Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
    headers.put(key, "Bearer $token")
    applier.apply(headers)
  }

  public override fun thisUsesUnstableApi() {}
}

class AgentClient(private val channel: ManagedChannel, private val token: String) {
  private val stub: AgentServiceCoroutineStub =
      AgentServiceCoroutineStub(channel).withCallCredentials(Creds(token = token))

  suspend fun createInvitation(label: String): Invitation {
    val response = stub.createInvitation(InvitationBase.newBuilder().setLabel(label).build())

    return response
  }

  suspend fun listen(): Flow<AgentStatus> {
    val uuid: String = UUID.randomUUID().toString()
    return stub.listen(ClientID.newBuilder().setID(uuid).build())
  }
}
