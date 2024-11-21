import com.rayadev.domain.usecase.GetUserUseCase
import com.rayadev.domain.repository.UserRepository
import com.rayadev.domain.model.UserResponse
import com.rayadev.domain.model.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `given repository returns success when invoke is called, then return success result`() = runTest {
        val page = 1
        val perPage = 10
        val mockResponse = UserResponse(
            users = listOf(User(id = 1, first_name = "John", last_name = "Doe", email = "john@example.com", avatar = "avatar_url")),
            totalPages = 5
        )

        `when`(userRepository.getUsers(page, perPage)).thenReturn(mockResponse)

        val result = getUserUseCase.invoke(page, perPage)

        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun `given repository throws exception when invoke is called, then return failure result`() = runTest {
        val page = 1
        val perPage = 10
        val mockException = Exception("Error fetching users")

        `when`(userRepository.getUsers(page, perPage)).thenThrow(mockException)

        val result = getUserUseCase.invoke(page, perPage)

        assertTrue(result.isFailure)
        assertEquals(mockException, result.exceptionOrNull())
    }
}