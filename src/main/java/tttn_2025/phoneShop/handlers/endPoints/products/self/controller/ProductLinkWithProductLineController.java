package tttn_2025.phoneShop.handlers.endPoints.products.self.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tttn_2025.phoneShop.handlers.endPoints.productLines.self.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.products.self.service.ProductService;

@RestController
@RequestMapping("/products/{productUid}/product-lines")
public class ProductLinkWithProductLineController {

    private final ProductService productService;

    public ProductLinkWithProductLineController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductLineDto>> readProductLines(
            @PathVariable(name = "productUid") UUID productUid,
            @RequestParam(defaultValue = "true", name = "linked") Boolean linked) {
        return ResponseEntity.ok().body(productService.readAllProductLines(productUid, linked));
    }

    @PatchMapping("/{productLineUid}")
    public ResponseEntity<?> updateProductLineLinks(
            @PathVariable(name = "productUid") UUID productUid,
            @PathVariable(name = "productLineUid") UUID productLineUid,
            @RequestParam(defaultValue = "false", name = "isRemove") Boolean isRemove) {
        if (isRemove) {
            productService.removeProductLines(productUid, List.of(productLineUid));
        } else {
            productService.addProductLines(productUid, List.of(productLineUid));
        }
        return ResponseEntity.ok().body(null);
    }
}
