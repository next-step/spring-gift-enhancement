package gift.product.controller;

import gift.product.dto.ProductRequest;
import gift.product.entity.Product;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin/products")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "productList";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest("", null, ""));
        return "productAddEdit";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("productRequest") ProductRequest request,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productAddEdit";
        }

        productService.create(request);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }

        ProductRequest request = new ProductRequest(product.getName(), product.getPrice(), product.getImgUrl());
        model.addAttribute("productRequest",request);
        model.addAttribute("productId", id);
        return "productAddEdit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("productRequest") ProductRequest request,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", id);
            return "productAddEdit";
        }

        productService.update(id,request);
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}

