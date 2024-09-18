import java.sql.{Connection, DriverManager, ResultSet}

object main {
  def main(args: Array[String]): Unit = {
    val url = "jdbc:postgresql://localhost:5432/maindatabase"
    val username = "postgres"
    val password = "matsalayev"

    var connection: Connection = null

    try {
      Class.forName("org.postgresql.Driver")

      connection = DriverManager.getConnection(url, username, password)

      val userRepository = new UserRepositoryImpl(connection)

//      userRepository.create(User(2,"Karim"))
//      println("Foydalanuvchi yaratildi.")

      val user = userRepository.read(1).get.name
      println(s"Foydalanuvchi o'qildi: $user")

//      userRepository.update(User(1, "Azizbek Matsalayev"))
//      println("Foydalanuvchi yangilandi.")

//      userRepository.delete(1)
//      println("Foydalanuvchi o'chirildi.")

    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (connection != null) {
        connection.close()
      }
    }
  }
}
