package org.findy_network.findy_common_kt

import io.grpc.ManagedChannelBuilder


class Connection(val authUrl: String, val authOrigin: String, val userName: String, val seed: String, val key: String, val server: String, val port: Int) {
    val agentClient: AgentClient

    override fun toString(): String {
        return "Connection(authUrl='$authUrl', authOrigin='$authOrigin', userName='$userName', seed='$seed', key='$key', server='$server', port=$port)"
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
        val acator: Acator = Acator(authUrl = authUrl, authOrigin = authOrigin, userName = userName, seed = seed, key = key)
        val token = acator.login()
        println(token)

        val channel = ManagedChannelBuilder.forAddress(server, port).useTransportSecurity().build()

        this.agentClient = AgentClient(channel, token)

    }

}
