package de.imedia24.shop.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun productsApi(): GroupedOpenApi {
        return GroupedOpenApi.builder().group("Products").pathsToMatch("/products/**").build()
    }

    @Bean
    fun springShopOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("Imedia24 Store API").description("This is a sample Store API using Kotlin")
                .version("v0.0.1")
        )
    }
}
