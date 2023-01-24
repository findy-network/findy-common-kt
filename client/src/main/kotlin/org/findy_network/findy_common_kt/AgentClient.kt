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

  suspend fun useAutoAccept() {
    stub.enter(
        ModeCmd.newBuilder()
            .setTypeID(ModeCmd.CmdType.ACCEPT_MODE)
            .setIsInput(true)
            .setAcceptMode(
                ModeCmd.AcceptModeCmd.newBuilder()
                    .setMode(ModeCmd.AcceptModeCmd.Mode.AUTO_ACCEPT)
                    .build())
            .build())
  }

  suspend fun createInvitation(label: String): Invitation =
    stub.createInvitation(
      InvitationBase.newBuilder()
        .setLabel(label)
        .build()
    )

  suspend fun createSchema(name: String, attributes: List<String>, version: String): Schema =
    stub.createSchema(
      SchemaCreate.newBuilder()
        .setName(name)
        .addAllAttributes(attributes)
        .setVersion(version)
        .build()
    )

  suspend fun getSchema(id: String): SchemaData =
    stub.getSchema(
      Schema.newBuilder()
        .setID(id)
        .build()
    )

  suspend fun createCredDef(schemaId: String, tag: String): CredDef =
    stub.createCredDef(
      CredDefCreate.newBuilder()
        .setSchemaID(schemaId)
        .setTag(tag)
        .build()
    )

  suspend fun getCredDef(id: String): CredDefData =
    stub.getCredDef(
      CredDef.newBuilder()
        .setID(id)
        .build()
    )

    suspend fun listen(): Flow<AgentStatus> =
    stub.listen(
      ClientID.newBuilder()
        .setID(UUID.randomUUID()
        .toString())
        .build()
      )
}
