package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "productList";
    }
}
