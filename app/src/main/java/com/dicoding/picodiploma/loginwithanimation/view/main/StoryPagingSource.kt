import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.loginwithanimation.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiService
import retrofit2.Callback

class StoryPagingSource(
    private val apiService: ApiService,
    private val token: String
) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        apiService.getAllStories("Bearer $token", position, params.loadSize)
            .enqueue(object : Callback<StoryResponse>{
                override fun onResponse(
                    call: retrofit2.Call<StoryResponse>,
                    response: retrofit2.Response<StoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val reponseData = response.body()?.listStory ?: emptyList()
                        LoadResult.Page(
                            data = reponseData,
                            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                            nextKey = if (reponseData.isEmpty()) null else position + 1
                        )
                    }
                    else{
                        LoadResult.Error(Exception("Error: ${response.message()}"))
                    }
                }
                override fun onFailure(call: retrofit2.Call<StoryResponse>, t: Throwable) {
                    LoadResult.Error(Exception("Error: ${t.message}"))
                }

            })
    }
//        return try {
//            // Panggil API secara langsung
//            val response = apiService.getAllStories("Bearer $token", position, params.loadSize).execute()
//            if (response.isSuccessful) {
//                val responseData = response.body()?.listStory ?: emptyList()
//                LoadResult.Page(
//                    data = responseData,
//                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//                    nextKey = if (responseData.isEmpty()) null else position + 1
//                )
//            } else {
//                Log.e("StoryPagingSource", "Error: ${response.message()}")
//                LoadResult.Error(Exception("Error: ${response.message()}"))
//
//            }
//        } catch (exception: Exception) {
//            Log.e("StoryPagingSource", "Error: ${exception.message}")
//
//            LoadResult.Error(exception)
//        }
//    }
}
