package de.imedia24.shop

import de.imedia24.shop.domain.product.ProductRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import java.math.BigDecimal


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShopApplicationTests {
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate

	@Test
	fun create_new_product() {
		val productRequest = ProductRequest("101","iPhone", BigDecimal(7000),"iPhone 12 PRO")
		val response: ResponseEntity<ProductRequest> = testRestTemplate
			.postForEntity("/products", productRequest,
				ProductRequest::class.java)
		Assertions.assertNotNull(response)
		Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
		Assertions.assertEquals(productRequest, response.body)
	}

}
