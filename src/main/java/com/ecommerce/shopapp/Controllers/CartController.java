package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        List<ProductResponseDTO> cart = (List<ProductResponseDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        ProductResponseDTO product = productService.getById(productId).getData();
        if (product != null) {
            cart.add(product);
            session.setAttribute("cart", cart);
            // Son eklenen ürünün mağaza slug'ını session'a kaydet
            if (product.getStore() != null && product.getStore().getSlug() != null) {
                session.setAttribute("lastStoreSlug", product.getStore().getSlug());
            }
            redirectAttributes.addFlashAttribute("message", "Ürün sepete eklendi!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Ürün bulunamadı!");
        }
        // Sepet sayfasına yönlendir
        return "redirect:/cart";
    }

    // Sepeti görüntüle
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<ProductResponseDTO> cart = (List<ProductResponseDTO>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(p -> p.getId().equals(productId));
            session.setAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("message", "Ürün sepetten çıkarıldı!");
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/cart");
    }

    @org.springframework.web.bind.annotation.GetMapping("/cart")
    public String viewCart(HttpSession session, org.springframework.ui.Model model) {
        List<ProductResponseDTO> cart = (List<ProductResponseDTO>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        model.addAttribute("cart", cart);
        return "cart";
    }
} 