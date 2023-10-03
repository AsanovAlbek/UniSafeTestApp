package albek.petprojects.unisafetestapp.feature.shopping_list_detail.data

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.model.ShoppingItemDto
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.model.ShoppingListDetailDto
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.model.ShoppingItem
import albek.petprojects.unisafetestapp.network.model.ShoppingItemRemote
import albek.petprojects.unisafetestapp.network.model.ShoppingListRemote

fun ShoppingListRemote.toDetailData() = ShoppingListDetailDto(
    id = id,
    name = name,
    created = createdDateTime
)

fun ShoppingItemRemote.toDetailData() = ShoppingItemDto(
    id = id,
    name = name,
    count = count
)

fun ShoppingItemDto.toDomain() = ShoppingItem(
    id = id,
    name = name,
    itemCount = count
)