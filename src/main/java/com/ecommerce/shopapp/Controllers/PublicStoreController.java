package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.StoreService;
import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PublicStoreController {
    private final StoreService storeService;
    private final ProductService productService;

    public PublicStoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping("/{slug:[a-zA-Z0-9_-]+}")
    public String getStoreBySlug(@PathVariable String slug, Model model) {
        DataResult<StoreResponseDTO> result = storeService.getStoreBySlug(slug);
        if (!result.isSuccess()) {
            model.addAttribute("error", result.getMessage());
            return "stores/storeDetailPage";
        }
        StoreResponseDTO store = result.getData();
        model.addAttribute("store", store);
        model.addAttribute("products", productService.getProductsByStoreIds(java.util.List.of(store.getId())).getData());
        return "stores/storeDetailPage";
    }
} 