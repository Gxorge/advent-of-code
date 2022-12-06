package uk.hotten.adventofcode

import java.io.File
import java.math.BigInteger
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.security.MessageDigest
import kotlin.jvm.Throws

abstract class AOCDay(private val year: Int, private val input: String) {

    @Throws(Exception::class)
    abstract fun part1()

    @Throws(Exception::class)
    abstract fun part2()

    fun getInput(): List<String> {
        return getFile("$year/$input")
    }

    private fun getFile(fileName: String): List<String> {
        val file = getFileFromResource(fileName) ?: return listOf()

        var lines: List<String> = listOf()
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return lines
    }

    private fun getFileFromResource(fileName: String): File? {
        val classLoader = javaClass.classLoader;
        val resource: URL = classLoader.getResource(fileName) ?: return null

        return File(resource.toURI())
    }

    fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')
}