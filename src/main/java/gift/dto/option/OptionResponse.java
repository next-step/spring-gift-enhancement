package gift.dto.option;

import gift.domain.Option;

public record OptionResponse(
  String name,
  int quantity
){
    public static OptionResponse from(Option option){
        return new OptionResponse(option.getName(), option.getQuantity());
    }
}
