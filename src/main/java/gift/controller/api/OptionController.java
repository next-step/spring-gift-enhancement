package gift.controller.api;

import gift.common.aop.annotation.PreAuthorize;
import gift.common.mapper.EntityToDtoMapper;
import gift.common.model.CustomAuth;
import gift.common.model.CustomPage;
import gift.common.validation.annotation.AllowedSortFields;
import gift.dto.option.CreateOptionRequest;
import gift.dto.option.OptionDefaultResponse;
import gift.dto.option.PatchOptionRequest;
import gift.dto.option.UpdateOptionRequest;
import gift.entity.Option;
import gift.entity.UserRole;
import gift.service.option.OptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<CustomPage<OptionDefaultResponse>> getOptions(
            @PathVariable Long productId,
            @AllowedSortFields(value = { "id", "name", "quantity" }, showAllowedFields = true)
            @PageableDefault(size = 5)
            Pageable pageable
    ) {
        var pagedOptions = CustomPage.convert(optionService.findAllBy(productId, pageable), EntityToDtoMapper::toDto);
        return ResponseEntity.ok(pagedOptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionDefaultResponse> getOptionById(
            @PathVariable Long productId,
            @PathVariable Long id
    ) {
        var option =  EntityToDtoMapper.toDto(optionService.findBy(id, productId));
        return ResponseEntity.ok(option);
    }

    @PreAuthorize(UserRole.ROLE_USER)
    @PostMapping
    public ResponseEntity<OptionDefaultResponse> createOption(
            @PathVariable Long productId,
            @RequestAttribute("auth") CustomAuth auth,
            @Valid @RequestBody CreateOptionRequest request
            ) {
        var savedOption =
                EntityToDtoMapper.toDto(optionService.create(productId, auth, request.name(), request.quantity()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOption);
    }

    @PreAuthorize(UserRole.ROLE_USER)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOption(
            @PathVariable Long productId,
            @PathVariable Long id,
            @RequestAttribute("auth") CustomAuth auth,
            @Valid @RequestBody UpdateOptionRequest request
    ) {
        var updatedOption = optionService.update(id, productId, auth, request.name(), request.quantity());
        if (updatedOption.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(EntityToDtoMapper.toDto(updatedOption.get()));
    }
    @PreAuthorize(UserRole.ROLE_USER)
    @PatchMapping("/{id}")
    public ResponseEntity<OptionDefaultResponse> updateOptionQuantity(
            @PathVariable Long productId,
            @PathVariable Long id,
            @RequestAttribute("auth") CustomAuth auth,
            @Valid @RequestBody PatchOptionRequest request
            ) {
        Optional<Option> updatedOption;
        if (request.increment()) {
            updatedOption = optionService.increaseQuantityBy(id, productId, auth, request.quantity());
        } else {
            updatedOption = optionService.decreaseQuantityBy(id, productId, auth, request.quantity());
        }
        return updatedOption.map(option -> ResponseEntity
                .ok(EntityToDtoMapper.toDto(option)))
                .orElseGet(() -> ResponseEntity.noContent().build()
                );
    }

    @PreAuthorize(UserRole.ROLE_USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable Long productId,
            @PathVariable Long id,
            @RequestAttribute("auth") CustomAuth auth
    ) {
        optionService.deleteBy(id, productId, auth);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(UserRole.ROLE_USER)
    @DeleteMapping
    public ResponseEntity<Void> deleteAllOptions(
            @PathVariable Long productId,
            @RequestAttribute("auth") CustomAuth auth
    ) {
        optionService.deleteAll(productId, auth);
        return ResponseEntity.noContent().build();
    }


}
