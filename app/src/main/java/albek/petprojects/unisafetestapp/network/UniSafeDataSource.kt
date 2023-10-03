package albek.petprojects.unisafetestapp.network

import albek.petprojects.unisafetestapp.core.model.UnsuccessfulResponseException
import albek.petprojects.unisafetestapp.network.retrofit.UniSafeApiHelper
import android.util.Log

class UniSafeDataSource(private val apiHelper: UniSafeApiHelper) {
    suspend fun getShoppingListById(id: Int) = apiHelper.getShoppingListById(listId = id)
        .apply { if (!isSuccess) throw UnsuccessfulResponseException() }

    suspend fun getAllShoppingLists() = apiHelper.getAllShoppingList()
        .apply { if (!isSuccess) throw UnsuccessfulResponseException() }

    suspend fun createShoppingList(name: String) = apiHelper.createShoppingList(name = name)
        .run { if (!isSuccess) throw UnsuccessfulResponseException() }

    suspend fun removeShoppingList(id: Int) = apiHelper.removeShoppingList(listId = id)
        .run { if (!isSuccess) throw UnsuccessfulResponseException() }

    /**
     * @param id - id списка покупок, в который добавляется покупка
     * @param name - наименование покупки
     * @param count - количество товара
     * */
    suspend fun addShoppingItem(id: Int, name: String, count: Int) =
        apiHelper.addToShoppingList(id = id, value = name, itemCount = count)
            .run { if (!isSuccess) throw UnsuccessfulResponseException() }

    /**
     * @param listId - id списка покупок, в котором удаляется покупка
     * @param itemId - id удаляемой покупки
     * @return Удаляет покупку из списка по указанному id
     * */
    suspend fun removeShoppingItem(listId: Int, itemId: Int) =
        apiHelper.removeFromShoppingList(listId = listId, itemId = itemId)
            .run { if (!isSuccess) throw UnsuccessfulResponseException() }
}