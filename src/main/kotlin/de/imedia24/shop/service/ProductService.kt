package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        return productRepository.findBySku(sku)?.toProductResponse()
    }

    fun findProductsBySku(skus:  List<String>): List<ProductResponse> {
        val productEntityList = productRepository.findBySkuIn(skus)
        return convertListOfEntitiesToResponseList(productEntityList)
    }

    fun convertListOfEntitiesToResponseList(productEntityList: List<ProductEntity>): List<ProductResponse>{
        val prodResList : MutableList<ProductResponse> = mutableListOf()
        productEntityList.forEach{
            prodResList.add(it.toProductResponse())
        }
        return prodResList
    }

    fun createProduct(product: ProductEntity): ProductResponse {
        if (productRepository.existsById(product.sku)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT, "SKU already exist"
            )
        }
        return productRepository.save(product).toProductResponse()
    }

    fun updateProduct(productRequest: ProductEntity): ProductResponse {
        val product = productRepository.findById(productRequest.sku)

        if (product.isEmpty)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "SKU NOT FOUND")

        return  productRepository.save(productRequest).toProductResponse()
    }


}
