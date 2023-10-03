package albek.petprojects.unisafetestapp.feature.shopping_list.data

import albek.petprojects.unisafetestapp.feature.shopping_list.data.model.ShoppingListDto
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import albek.petprojects.unisafetestapp.network.model.ShoppingListRemote

fun ShoppingListRemote.toData() = ShoppingListDto(
    id = id,
    name = name,
    created = createdDateTime
)

fun ShoppingListDto.toDomain() = ShoppingList(
    id = id,
    name = name,
    created = created
)