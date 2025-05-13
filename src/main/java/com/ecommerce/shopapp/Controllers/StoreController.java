package com.ecommerce.shopapp.Controllers;

import com.ecommerce.shopapp.Business.Abstracts.StoreService;
import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.Core.Utils.Helpers.ImageStorageService;
import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.UserRepository;
import com.ecommerce.shopapp.DTOs.Requests.StoreRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;
import com.ecommerce.shopapp.Entities.Concretes.User;
import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stores")
public class StoreController {
    private final UserService userService;
    private final StoreService storeService;
    private final ImageStorageService imageStorageService;
    private final UserRepository userRepository;
    private final ProductService productService;

    public StoreController(UserService userService, StoreService storeService, ImageStorageService imageStorageService, UserRepository userRepository, ProductService productService) {
        this.userService = userService;
        this.storeService = storeService;
        this.imageStorageService = imageStorageService;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    // ✅ Tüm mağazaları listele
    @GetMapping("/show-stores-page")
    public String getAllStores(Model model, HttpServletRequest request) {
        model.addAttribute("currentURI", request.getRequestURI());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("user", userService.getUserByEmail(email));
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Sadece bu kullanıcıya ait mağazaları getir
        DataResult<List<StoreResponseDTO>> result = storeService.getAllStoresByOwnerId(owner.getId());

        if (!result.isSuccess()) {
            model.addAttribute("error", result.getMessage()); // Hata mesajını göster
            return "stores/getAllStoresPage";
        }
        model.addAttribute("stores", result.getData());
        return "stores/getAllStoresPage"; // templates/store/list.html
    }

    // ✅ Yeni mağaza formu
    @GetMapping("/create-store-page")
    public String createStorePage(Model model, HttpServletRequest request) {
        model.addAttribute("currentURI", request.getRequestURI()); // eklendi
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        model.addAttribute("store", new StoreRequestDTO());
        return "stores/createStorePage";
    }

    // ✅ Yeni mağaza ekle
    @PostMapping("/create-store")
    public String createStore(@RequestParam("storeName") String storeName,
                           @RequestParam("description") String description,
                           @RequestParam("bannerImage") MultipartFile bannerImage,
                           @RequestParam("shopImage") MultipartFile shopImage,
                           @RequestParam("storeSlug") String storeSlug) {

        // Giriş yapan kullanıcıyı al
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Görselleri kaydet
        String bannerUrl = imageStorageService.saveImage(bannerImage);
        String shopUrl = imageStorageService.saveImage(shopImage);

        StoreRequestDTO dto = new StoreRequestDTO(storeName, description, bannerUrl, shopUrl, owner.getId(), storeSlug);
        storeService.createStore(dto);

        return "redirect:/stores/show-stores-page";
    }

    // ✅ Güncelleme formu
    @GetMapping("/update-store-page/{id}")
    public String updateStorePage(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        DataResult<StoreResponseDTO> result = storeService.getStoreById(id);
        if (!result.isSuccess()) {
            return "redirect:/stores/show-stores-page";
        }

        model.addAttribute("store", result.getData());
        return "stores/updateStorePage";
    }

    // ✅ Güncelleme işlemi
    @PostMapping("/update-store/{id}")
    public String updateStore(@PathVariable("id") Long id,
                              @RequestParam("storeName") String storeName,
                              @RequestParam("description") String description,
                              @RequestParam(value = "bannerImage", required = false) MultipartFile bannerImage,
                              @RequestParam(value = "shopImage", required = false) MultipartFile shopImage,
                              @RequestParam("slug") String slug,
                              RedirectAttributes redirectAttributes) {
        String bannerUrl = (bannerImage != null && !bannerImage.isEmpty()) ? imageStorageService.saveImage(bannerImage) : null;
        String shopUrl = (shopImage != null && !shopImage.isEmpty()) ? imageStorageService.saveImage(shopImage) : null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRequestDTO user = userService.getUserByEmail(auth.getName()).getData();
        Long ownerid = user.getId();
        StoreRequestDTO dto = new StoreRequestDTO(storeName, description, bannerUrl, shopUrl, ownerid, slug);
        Result result = storeService.updateStore(id, dto);

        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("success", result.isSuccess());

        return "redirect:/stores/show-stores-page";
    }

    // ✅ Sil
    @PostMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result result = storeService.deleteStore(id);

        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("success", result.isSuccess());

        return "redirect:/stores/show-stores-page";
    }

    // Public store products page
    @GetMapping("/{storeSlug}/products")
    public String getStoreProducts(@PathVariable String storeSlug, Model model) {
        DataResult<StoreResponseDTO> storeResult = storeService.getStoreBySlug(storeSlug);
        if (!storeResult.isSuccess()) {
            return "error/404";
        }

        StoreResponseDTO store = storeResult.getData();
        List<Long> storeIds = List.of(store.getId());
        DataResult<List<ProductResponseDTO>> productsResult = productService.getProductsByStoreIds(storeIds);

        model.addAttribute("store", store);
        model.addAttribute("products", productsResult.getData());
        return "public/storeProducts";
    }
}
