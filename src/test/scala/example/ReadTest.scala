import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement, ResultSet}

class ReadTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "UserRepositoryImpl" should "read a user by id" in {
    // Mock qilinayotgan ob'ektlar
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    // Mockito yordamida return qiymatlarni belgilash
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true)
    when(mockResultSet.getInt("id")).thenReturn(1)
    when(mockResultSet.getString("name")).thenReturn("Karim")

    // Repository sinfini mocklangan ulanish bilan yaratish
    val userRepository = new UserRepositoryImpl(mockConnection)

    // Foydalanuvchini o'qish
    val user = userRepository.read(1)

    // Test natijalari
    user shouldBe Some(User(1, "Karim"))

    // Mockning chaqirilganligini tekshirish
    verify(mockPreparedStatement).setInt(1, 1)
    verify(mockPreparedStatement).executeQuery()
    verify(mockPreparedStatement).close()
  }
}
