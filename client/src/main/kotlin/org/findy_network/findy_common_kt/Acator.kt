
package org.findy_network.findy_common_kt

import java.io.*
import java.util.concurrent.TimeUnit

interface Executor {
    fun run(command: String): String?
}

class Acator(val authUrl: String, val authOrigin: String, val userName: String, val seed: String, val key: String, val exec: Executor = Exec()) {

    val fcliBin = "findy-agent-cli"

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
        println("acator authUrl: $authUrl")
        println("acator authOrigin: $authOrigin")
        println("acator userName: $userName")

        val versionRes = exec.run(fcliBin + " --version")
        println("acator version: $versionRes")
    }

    fun login(): String {
        // First try login. If login fails, we probably haven't registered
        // so try to register and then login.
        try {
            val registerRes = exec.run(fcliBin + " authn register --url $authUrl --origin $authOrigin --user-name $userName --seed $seed --key $key")
            println("acator register result: $registerRes")
        } catch (e: Exception) {
            println("acator register failed: $e")
        }
        val loginRes = exec.run(fcliBin + " authn login --url $authUrl --origin $authOrigin --user-name $userName --key $key")
        println("acator login succeeded: $loginRes")
        return if (loginRes != null) loginRes.trim() else ""
    }
}
