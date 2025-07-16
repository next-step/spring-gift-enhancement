package gift.service;

import gift.dto.ItemRequest;
import gift.dto.ItemResponse;
import gift.entity.Item;
import gift.entity.Member;
import gift.entity.Role;
import gift.exception.AuthorizationException;
import gift.exception.ItemNotFoundException;
import gift.repository.ItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemResponse> getAllItems(int page, int size, String sortProperty, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Item> itemPage = itemRepository.findAll(pageable);

        return itemPage.getContent().stream()
            .map(ItemResponse::from)
            .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        return itemRepository.findById(id)
            .map(ItemResponse::from)
            .orElseThrow(() -> new ItemNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + id));
    }

    @Transactional
    public ItemResponse createItem(ItemRequest request, Member loginMember) {
        validateAdminRoleForKakaoKeyword(request.name(), loginMember);
        Item item = new Item(null, request.name(), request.price(), request.imageUrl());
        Item savedItem = itemRepository.save(item);
        return ItemResponse.from(savedItem);
    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemRequest request, Member loginMember) {
        validateAdminRoleForKakaoKeyword(request.name(), loginMember);
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException("수정할 상품을 찾을 수 없습니다: " + id));

        item.updateInfo(request.name(), request.price(), request.imageUrl());
        return ItemResponse.from(item);
    }

    @Transactional
    public void deleteItem(Long id, Member loginMember) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException("삭제할 상품을 찾을 수 없습니다: " + id));
        if (loginMember.getRole() != Role.ADMIN) {
            throw new AuthorizationException("상품을 삭제할 권한이 없습니다.");
        }
        itemRepository.deleteById(id);
    }

    private void validateAdminRoleForKakaoKeyword(String productName, Member member) {
        if (productName.contains("카카오") && member.getRole() != Role.ADMIN) {
            throw new AuthorizationException("카카오 관련 상품은 ADMIN만 등록할 수 있습니다.");
        }
    }
}