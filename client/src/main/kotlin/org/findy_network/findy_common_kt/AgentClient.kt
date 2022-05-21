package org.findy_network.findy_common_kt

import org.findy_network.findy_common_kt.AgentServiceGrpcKt.AgentServiceCoroutineStub
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.CallCredentials
import io.grpc.CallCredentials.*
import io.grpc.Metadata
import java.io.Closeable
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executor


class Creds(val token: String) : CallCredentials() {

    public override fun applyRequestMetadata(requestInfo: RequestInfo, appExecutor: Executor, applier: MetadataApplier) {
        var headers = Metadata()
        val key: Metadata.Key<String> =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        headers.put(key, "Bearer $token")
        applier.apply(headers)
    }

    public override fun thisUsesUnstableApi() {

    }

}

class AgentClient(private val channel: ManagedChannel, private val token: String) : Closeable {
    private val stub: AgentServiceCoroutineStub = AgentServiceCoroutineStub(channel).withCallCredentials(Creds(token = token))

    suspend fun createInvitation(label: String): Invitation {
        val response = stub.createInvitation(InvitationBase.newBuilder().setLabel(label).build())

        println("Received from Agency:\n$response")

        return response
    }
    
    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

