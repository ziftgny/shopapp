package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    // ✅ Ürün ekleme formunu aç
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "createProduct";
    }

    // ✅ Formdan gelen ürünü kaydet
    @PostMapping("newcreate")
    public String createProduct(@ModelAttribute Product product) {
        productService.add(product);
        return "redirect:/products";
    }
}
