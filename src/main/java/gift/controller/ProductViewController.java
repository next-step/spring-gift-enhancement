package gift.controller;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Option;
import gift.annotation.LoginMember;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    /*
    요런 식으로 구성이 되면
    LoginViewController에서 redirection하기 때문에 값이 제대로 전달이 되지 않음 따라서
    자꾸 member객체는 전부 null로 인식됨
    // 상품 목록 페이지
    @GetMapping
    public String list(Model model) {
        List<Product> products = productDao.showProducts();
        model.addAttribute("products", products);
        return "products/list";
    }
    */

    //따라서 로그인된 Member의 정보를 가져오기
    @GetMapping("/user/products")
    public String list(Model model,
                       @LoginMember Member member,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size) {
        if (member == null) {
            return "redirect:/login";
        }

        Page<Product> productPage = productService.findAll(
                PageRequest.of(page, size, Sort.by("name").ascending())
        );

        // 옵션은 이미 페치 조인되어 있으므로 별도로 조회할 필요 없음
        int maxPage = Math.max(3, productPage.getTotalPages());

        model.addAttribute("productPage", productPage);
        model.addAttribute("member", member);
        model.addAttribute("maxPage", maxPage);

        return "products/list";
    }
}
