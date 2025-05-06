package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.Business.Abstracts.StoreService;
import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.Core.Utils.Helpers.ImageStorageService;
import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import com.ecommerce.shopapp.Entities.Concretes.Store;
import com.ecommerce.shopapp.Entities.Concretes.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ImageStorageService imageStorageService;
    private final StoreService storeService;

    public ProductController(ProductService productService, UserService userService, ImageStorageService imageStorageService, StoreService storeService, StoreService storeService1) {
        this.productService = productService;
        this.userService = userService;
        this.imageStorageService = imageStorageService;
        this.storeService = storeService1;
    }

    @GetMapping("/products")
    public String getAllProducts(@RequestParam(name = "storeId", required = false) Long storeId, Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        DataResult<UserRequestDTO> userResult = userService.getUserByEmail(email);
        UserRequestDTO user = userResult.getData();
        DataResult<List<StoreResponseDTO>> storeResult = storeService.getAllStoresByOwnerId(user.getId());

        List<Long> storeIds = (storeId != null)
                ? List.of(storeId)
                : storeResult.getData().stream().map(StoreResponseDTO::getId).toList();
        DataResult<List<ProductResponseDTO>> result = productService.getProductsByStoreIds(storeIds);
        if (!result.isSuccess()) {
            model.addAttribute("error", result.getMessage()); // Hata mesajını göster
        }
        model.addAttribute("currentURI", request.getRequestURI());
        model.addAttribute("user", userService.getUserByEmail(email));
        model.addAttribute("products", result.getData());
        model.addAttribute("stores", storeResult.getData());
        model.addAttribute("storeId", storeId);
        return "productList";
    }

    @GetMapping("/create-product-page")
    public String CreateProductPage(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        DataResult<UserRequestDTO> user = userService.getUserByEmail(email);
        DataResult<List<StoreResponseDTO>> userStores = storeService.getAllStoresByOwnerId(user.getData().getId());

        model.addAttribute("currentURI", request.getRequestURI());
        model.addAttribute("user", user);
        model.addAttribute("product", new ProductRequestDTO());
        model.addAttribute("stores", userStores.getData());
        return "createProductPage";
    }

    @PostMapping("/create-product")
    public String createProduct(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam double price,
                                @RequestParam("storeId") Long storeId,
                                @RequestParam("productImage") MultipartFile productImage, Model model) {
        String imageUrl = imageStorageService.saveImage(productImage);
        ProductRequestDTO dto = new ProductRequestDTO(name, description, price, imageUrl);

        // Store ID'yi de ek bilgi olarak servise gönder
        productService.createWithStore(dto, storeId); // bu yeni metot olacak

        return "redirect:/products";
    }

    @GetMapping("/update-product-page/{id}")
    public String updateProductPage(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        DataResult<UserRequestDTO> user = userService.getUserByEmail(email);
        DataResult<List<StoreResponseDTO>> userStores = storeService.getAllStoresByOwnerId(user.getData().getId());
        DataResult<ProductResponseDTO> result = productService.getById(id);
        model.addAttribute("product", result.getData());
        model.addAttribute("user", user);
        model.addAttribute("stores", userStores.getData());
        return "updateProductPage";
    }
    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("price") Double price,
                                @RequestParam(value = "productImage", required = false) MultipartFile productImage) {

        String imageUrl = (productImage != null && !productImage.isEmpty())
                ? imageStorageService.saveImage(productImage)
                : null;

        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setImageUrl(imageUrl);

        productService.update(id, dto);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result result = productService.delete(id);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("success", result.isSuccess());
        return "redirect:/products";
    }
}
