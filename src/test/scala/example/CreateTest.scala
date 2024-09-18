import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement, ResultSet}

class CreateTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "UserRepositoryImpl" should "create a user" in {
    // Mock qilinayotgan ob'ektlar
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    // Mockito yordamida return qiymatlarni belgilash
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true)
    when(mockResultSet.getInt(1)).thenReturn(1)  // ID qiymati

    // Repository sinfini mocklangan ulanish bilan yaratish
    val userRepository = new UserRepositoryImpl(mockConnection)

    // Amallarni bajarish
    userRepository.create(User(0, "Karim"))

    // Mockning chaqirilganligini tekshirish
    verify(mockPreparedStatement).setString(1, "Karim")
    verify(mockPreparedStatement).executeQuery()
    verify(mockPreparedStatement).close()
  }
}
