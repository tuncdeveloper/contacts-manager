package database;

import domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDb {

    private ConnectDb connectDb;

    public PersonDb() {
        this.connectDb = ConnectDb.instance();
    }

    // Kişi ekleme
    public void addPerson(Person person) {
        String query = "INSERT INTO persons (name, surname, phone_number) VALUES (?, ?, ?)";

        try (Connection connection = connectDb.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, person.getName());
            stmt.setString(2, person.getSurname());
            stmt.setString(3, person.getPhoneNumber());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding person", e);
        }
    }

    // Kişi güncelleme
    public void updatePerson(Person person) {
        String query = "UPDATE persons SET name = ?, surname = ?, phone_number = ? WHERE person_id = ?";

        try (Connection connection = connectDb.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, person.getName());
            stmt.setString(2, person.getSurname());
            stmt.setString(3, person.getPhoneNumber());
            stmt.setInt(4, person.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating person", e);
        }
    }

    // Kişi silme
    public void deletePerson(int id) {
        String query = "DELETE FROM persons WHERE person_id = ?";

        try (Connection connection = connectDb.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting person", e);
        }
    }

    // Kişi listeleme
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons";

        try (Connection connection = connectDb.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Person person = new Person(
                        rs.getInt("person_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone_number")
                );
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving persons", e);
        }

        return persons;
    }

    // Kişi arama
    public Person getPersonById(int id) {
        String query = "SELECT * FROM persons WHERE person_id = ?";

        try (Connection connection = connectDb.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Person(
                        rs.getInt("person_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving person by ID", e);
        }

        return null; // Kişi bulunamazsa null döndürülür
    }
}
