package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAll().getData());
        return "productList";
    }

    @GetMapping("/create-product-page")
    public String CreateProductPage(Model model) {
        model.addAttribute("product", new ProductRequestDTO());
        return "createProductPage";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute ProductRequestDTO productRequestDTO, Model model) {
        Result result = productService.create(productRequestDTO);
        return "redirect:/create-product-page";
    }

    @GetMapping("/update-product-page/{id}")
    public String updateProductPage(@PathVariable Long id, Model model) {
        DataResult<ProductResponseDTO> result = productService.getById(id);
        model.addAttribute("product", result.getData());
        return "updateProductPage";
    }
    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute ProductResponseDTO dto) {
        ProductRequestDTO updateDto = new ProductRequestDTO();
        updateDto.setName(dto.getName());
        updateDto.setDescription(dto.getDescription());
        updateDto.setPrice(dto.getPrice());
        updateDto.setImageUrl(dto.getImageUrl());

        productService.update(dto.getId(), updateDto);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
