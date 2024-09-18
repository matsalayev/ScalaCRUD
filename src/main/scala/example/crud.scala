import java.sql.{Connection, DriverManager, ResultSet}

case class User(id: Int, name: String)

trait UserRepository {
  def create(user: User): Unit
  def read(id: Int): Option[User]
  def update(user: User): Unit
  def delete(id: Int): Unit
}

class UserRepositoryImpl(connection: Connection) extends UserRepository {

  override def create(user: User): Unit = {
    val query = "INSERT INTO users (name) VALUES (?) RETURNING id"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, user.name)

    val resultSet = preparedStatement.executeQuery()
    if (resultSet.next()) {
      val generatedId = resultSet.getInt(1)
      println(s"Yangi foydalanuvchi yaratildi, ID: $generatedId")
    }

    preparedStatement.close()
  }


  override def read(id: Int): Option[User] = {
    val query = "SELECT * FROM users WHERE id = ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setInt(1, id)
    val resultSet = preparedStatement.executeQuery()

    if (resultSet.next()) {
      val user = User(resultSet.getInt("id"), resultSet.getString("name"))
      preparedStatement.close()
      Some(user)
    } else {
      preparedStatement.close()
      None
    }
  }

  override def update(user: User): Unit = {
    val query = "UPDATE users SET name = ? WHERE id = ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, user.name)
    preparedStatement.setInt(2, user.id)
    preparedStatement.executeUpdate()
    preparedStatement.close()
  }

  override def delete(id: Int): Unit = {
    val query = "DELETE FROM users WHERE id = ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setInt(1, id)
    preparedStatement.executeUpdate()
    preparedStatement.close()
  }
}
