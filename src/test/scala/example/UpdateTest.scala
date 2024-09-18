import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement}

class UpdateTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "UserRepositoryImpl" should "update a user" in {
    // Mock qilinayotgan ob'ektlar
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    // Mockito yordamida return qiymatlarni belgilash
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

    // Repository sinfini mocklangan ulanish bilan yaratish
    val userRepository = new UserRepositoryImpl(mockConnection)

    // Foydalanuvchini yangilash
    userRepository.update(User(1, "Karim Updated"))

    // Mockning chaqirilganligini tekshirish
    verify(mockPreparedStatement).setString(1, "Karim Updated")
    verify(mockPreparedStatement).setInt(2, 1)
    verify(mockPreparedStatement).executeUpdate()
    verify(mockPreparedStatement).close()
  }
}
