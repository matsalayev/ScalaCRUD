import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement}

class DeleteTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "UserRepositoryImpl" should "delete a user by id" in {
    // Mock qilinayotgan ob'ektlar
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    // Mockito yordamida return qiymatlarni belgilash
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

    // Repository sinfini mocklangan ulanish bilan yaratish
    val userRepository = new UserRepositoryImpl(mockConnection)

    // Foydalanuvchini o'chirish
    userRepository.delete(1)

    // Mockning chaqirilganligini tekshirish
    verify(mockPreparedStatement).setInt(1, 1)
    verify(mockPreparedStatement).executeUpdate()
    verify(mockPreparedStatement).close()
  }
}
