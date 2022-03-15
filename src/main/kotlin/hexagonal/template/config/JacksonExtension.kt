package hexagonal.template.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream
import java.time.format.DateTimeFormatterBuilder

object JacksonExtension {

    val jacksonObjectMapper: ObjectMapper by lazy {
        val objectMapper = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(Jdk8Module())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false)
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        objectMapper
    }
}

object JacksonSnakeCaseExtension {

    val jacksonObjectMapper: ObjectMapper by lazy {
        val objectMapper = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(Jdk8Module())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false)
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE

        objectMapper
    }
}

fun <T> InputStream.jsonToObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

fun <T> String.jsonToObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

fun <T> String.jsonToList(t: Class<T>): List<T> {
    val listType = JacksonExtension.jacksonObjectMapper.typeFactory.constructCollectionType(ArrayList::class.java, t)
    return JacksonExtension.jacksonObjectMapper.readValue(this, listType)
}

fun <T> JsonNode.toObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.treeToValue(this, t)

fun <T> T.objectToJson(): String =
    JacksonExtension.jacksonObjectMapper.writeValueAsString(this)

fun <T> T.objectToJsonAsSnakeCase(): String =
    JacksonSnakeCaseExtension.jacksonObjectMapper.writeValueAsString(this)

fun <T> T.objectToJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.valueToTree(this)

fun <T> T.objectToJsonNodeAsSnakeCase(): JsonNode =
    JacksonExtension.jacksonObjectMapper.valueToTree(this)

private class CustomInstantSerializer :
    InstantSerializer(INSTANCE, false, DateTimeFormatterBuilder().appendInstant(3).toFormatter())
