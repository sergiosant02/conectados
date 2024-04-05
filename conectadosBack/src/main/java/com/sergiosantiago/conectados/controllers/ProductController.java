package com.sergiosantiago.conectados.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiosantiago.conectados.Utils.Utils;
import com.sergiosantiago.conectados.dtos.ProductDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.services.ProductService;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping("/products")
public class ProductController {

    private UserService userService;
    private ProductService productService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public HttpResponse<ProductDTO> createProduct(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductDTO productDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return productService.createproduct(user, productDTO);
    }

    @PostMapping("/delete")
    public HttpResponse<ProductDTO> deleteProduct(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductDTO productDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return productService.deleteProduct(user, productDTO);
    }

    @PostMapping("/assignCategories")
    public HttpResponse<ProductDTO> assignCategorieToProduct(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductDTO productDTO) {
        return productService.assignCategory(productDTO);
    }

    @PostMapping("/unassignCategories")
    public HttpResponse<ProductDTO> unassignCategorieToProduct(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductDTO productDTO) {
        return productService.unassignCategory(productDTO);
    }

}
