package gift.dto.wishlist;


import gift.domain.WishList;

public record WishListResponse(
   Long memberId,
   String memberEmail,

   Long productId,
   String productName,
   int productPrice,
   int quantity,
   int totalPrice
) {

    public static WishListResponse from(WishList entity){
        return new WishListResponse(
            entity.getMember().getId(),
            entity.getMember().getEmail(),
            entity.getProduct().getId(),
            entity.getProduct().getName(),
            entity.getProduct().getPrice(),
            entity.getQuantity(),
            entity.getQuantity() * entity.getProduct().getPrice()
        );
    }

}
