package de.imedia24.shop.controller

import de.imedia24.shop.db.entity.ProductEntity.Companion.toProductEntity
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("products")
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("{sku}")
    fun findProductBySku(@PathVariable("sku") sku: String): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if (product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping
    fun findProductListBySkuList(@RequestParam("sku") skuList: List<String>): ResponseEntity<List<ProductResponse>> {
        logger.info("Request for products $skuList")

        val products: List<ProductResponse> = productService.findProductsBySku(skuList)
        return if (products.isEmpty()) {
                ResponseEntity.notFound().build()
        } else {
                ResponseEntity.ok(products)
        }
    }

    @PostMapping
    fun createProduct(@RequestBody product: @Valid ProductRequest): ResponseEntity<ProductResponse> {
        val productResponse = productService.createProduct(product.toProductEntity())
        val location = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(productResponse.sku)
            .toUri()
        return ResponseEntity.created(location).body(productResponse)
    }

    @PatchMapping
    fun patchProduct(@RequestBody product: @Valid ProductRequest): ResponseEntity<ProductResponse?>? {
        return ResponseEntity.ok(productService.updateProduct(product.toProductEntity()))
    }

}
