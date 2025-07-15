package gift.product.controller.view;

import gift.product.domain.Product;
import gift.product.dto.ProductPatchRequestDto;
import gift.product.dto.ProductSaveRequestDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/admin/product")
public class ProductAdminViewController {
    private final ProductService productService;

    public ProductAdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("productSaveRequestDto", new ProductSaveRequestDto());
        return "productAddForm";
    }

    @PostMapping("/add")
    public String createProduct(@Valid @ModelAttribute ProductSaveRequestDto productSaveRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productAddForm";
        }
        productService.createProduct(productSaveRequestDto);
        return "redirect:/api/admin/product/list";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        ProductPatchRequestDto productPatchRequestDto = new ProductPatchRequestDto(product);
        model.addAttribute("productPatchRequestDto", productPatchRequestDto);
        return "productUpdateForm";
    }

    @PatchMapping("/{id}/update")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductPatchRequestDto productPatchRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productUpdateForm";
        }
        productService.updateProduct(id, productPatchRequestDto);
        return "redirect:/api/admin/product/list";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/admin/product/list";
    }
}
