package albek.petprojects.unisafetestapp.network.retrofit

import albek.petprojects.unisafetestapp.network.model.AddShoppingItemResponse
import albek.petprojects.unisafetestapp.network.model.AddShoppingListResponse
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.API_KEY
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ITEM_COUNT
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ITEM_ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.KEY
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.LIST_ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.NAME
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.VALUE
import albek.petprojects.unisafetestapp.network.model.RemoveResponse
import albek.petprojects.unisafetestapp.network.model.ShoppingItemListRemote
import albek.petprojects.unisafetestapp.network.model.ShoppingListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UniSafeApiHelper {
    @GET("GetShoppingList")
    suspend fun getShoppingListById(@Query(LIST_ID) listId: Int): ShoppingItemListRemote

    @GET("GetAllMyShopLists")
    suspend fun getAllShoppingList(@Query(KEY) apiKey: String = API_KEY): ShoppingListResponse

    @POST("CreateShoppingList")
    suspend fun createShoppingList(
        @Query(KEY) apiKey: String = API_KEY,
        @Query(NAME) name: String
    ): AddShoppingListResponse

    @POST("RemoveShoppingList")
    suspend fun removeShoppingList(
        @Query(KEY) apiKey: String = API_KEY,
        @Query(LIST_ID) listId: Int
    ): RemoveResponse

    @POST("AddToShoppingList")
    suspend fun addToShoppingList(
        @Query(ID) id: Int,
        @Query(VALUE) value: String = "",
        @Query(ITEM_COUNT) itemCount: Int = 1
    ): AddShoppingItemResponse

    @POST("RemoveFromList")
    suspend fun removeFromShoppingList(
        @Query(LIST_ID) listId: Int,
        @Query(ITEM_ID) itemId: Int
    ): RemoveResponse
}