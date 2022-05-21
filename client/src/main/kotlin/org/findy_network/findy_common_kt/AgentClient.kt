package org.findy_network.findy_common_kt

import org.findy_network.findy_common_kt.AgentServiceGrpcKt.AgentServiceCoroutineStub
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class AgentClient(private val channel: ManagedChannel) : Closeable {
    private val stub: AgentServiceCoroutineStub = AgentServiceCoroutineStub(channel)
    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

