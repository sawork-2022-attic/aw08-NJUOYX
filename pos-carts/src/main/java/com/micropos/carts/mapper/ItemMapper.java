package com.micropos.carts.mapper;

import com.micropos.carts.dto.ItemDto;
import com.micropos.carts.dto.ProductDto;
import com.micropos.carts.model.Item;
import com.micropos.products.model.Product;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface ItemMapper {
    ItemDto toItemDto(Item item);
    Item toItem(ItemDto itemDto);
    Collection<Item> toItems(Collection<ItemDto> itemsDto);
    Collection<ItemDto> toItemsDto(Collection<Item>items);
    Product toProduct(ProductDto productDto);
}
