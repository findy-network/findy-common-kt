package org.findy_network.findy_common_kt

import java.io.*
import java.util.concurrent.TimeUnit

class Exec : Executor {
  override fun run(command: String): String? {
    return command.runCommand(File("."))
  }
}

fun String.runCommand(workingDir: File): String? {
  try {
    val parts = this.split("\\s".toRegex())
    val proc =
        ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    val res = proc.inputStream.bufferedReader().readText()
    println(this + "\n" + res)
    return res
  } catch (e: IOException) {
    e.printStackTrace()
    return null
  }
}
