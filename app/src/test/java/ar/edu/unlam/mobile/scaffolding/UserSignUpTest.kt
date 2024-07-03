package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.data.repository.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import ar.edu.unlam.mobile.scaffolding.domain.services.user.UserService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserSignUpTest {
    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    private lateinit var userService: UserUseCases
    private lateinit var currentUser: User

    @Before
    fun onBeforeInitTests() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository)
        currentUser =
            User(
                id = 1,
                firstName = "franco",
                lastName = "skurnik",
                age = 20,
                height = 180,
                weight = 85.50,
                kilometers = 2.0,
                calories = 500,
                minutes = 60,
                days = 5,
            )
    }

    @Test
    fun shouldSaveTheUserInDatabase() =
        runBlocking {
            // Given
            coEvery { userRepository.saveUser(currentUser) } returns Unit

            // When
            userService.saveUser(currentUser)

            coVerify(exactly = 1) { userRepository.saveUser(currentUser) }
        }

    @Test
    fun shouldGetTheUserWithIdOne() =
        runBlocking {
            // given
            coEvery { userRepository.getUser(1L) } returns flowOf(currentUser)

            // when
            userService.getUser(1)

            // then
            coVerify(exactly = 1) { userRepository.getUser(1L) }
        }
}
