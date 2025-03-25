import app.cash.turbine.test
import com.example.newsappfordataminds.data.model.Article
import com.example.newsappfordataminds.data.model.NewsResponse
import com.example.newsappfordataminds.data.model.Source
import com.example.newsappfordataminds.domain.usecase.RecentNewsUseCase
import com.example.newsappfordataminds.presentation.model.RecentNewsUiState
import com.example.newsappfordataminds.presentation.viewmodels.RecentNewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
internal class RecentNewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val recentNewsUseCase: RecentNewsUseCase = mockk()
    private lateinit var viewModel: RecentNewsViewModel

    @Before
    fun setUp() {
        viewModel = RecentNewsViewModel(recentNewsUseCase)
    }
    @After
    fun tearDown(){
        mainDispatcherRule.testDispatcher
    }

    @Test
    fun `getRecentNews emits Success when every thing went well`() = runTest {
        coEvery {
            recentNewsUseCase(
                query = "tesla",
                from = "2025-02-24",
                apiKey = "325fe0100ad94b8bbe939a7ba7e08916",
                sortBy = "publishedAt"
            )
        } returns NewsResponse(status = "ok", 2, articles)

        viewModel.newsState.test(30.seconds) {
            assertEquals(RecentNewsUiState.Loading, awaitItem())
            viewModel.getRecentNews(
                query = "tesla",
                fromDate = "2025-02-24",
                apiKey = "325fe0100ad94b8bbe939a7ba7e08916",
                sortBy = "publishedAt"
            )
            val result = awaitItem()
            assertTrue(result is RecentNewsUiState.Success)
        }
    }
    @Test
    fun `getRecentNews emits Error when anything went wrong`() = runTest {
        coEvery {
            recentNewsUseCase(
                query = "tesla",
                from = "2025-02-24",
                apiKey = "325fe0100ad94b8bbe939a7ba7e08916",
                sortBy = "publishedAt"
            )
        }  throws Exception("NetworkEXCEPTION")

        viewModel.getRecentNews(
            query = "tesla",
            fromDate = "2025-02-24",
            apiKey = "325fe0100ad94b8bbe939a7ba7e08916",
            sortBy = "publishedAt"
        )
        viewModel.newsState.test(30.seconds) {

            val result = awaitItem()
            assertTrue(result is RecentNewsUiState.Error)
        }
    }

    companion object {
        private val articles = listOf(
            Article(
                title = "Tesla Stock Rises",
                description = "Tesla stock is surging after earnings.",
                urlToImage = "https://example.com/tesla.jpg",
                source = Source(id = "1", name = "CNBC"),
                publishedAt = "2025-02-24T10:00:00Z",
                url = "d",
                author = "s"
            ), Article(
                title = "Tesla Expands to Europe",
                description = "Tesla is launching new factories in Germany.",
                urlToImage = "https://example.com/tesla2.jpg",
                source = Source(id = "2", name = "BBC"),
                publishedAt = "2025-02-25T12:00:00Z",
                url = "d",
                author = "s"
            )
        )
    }
}
