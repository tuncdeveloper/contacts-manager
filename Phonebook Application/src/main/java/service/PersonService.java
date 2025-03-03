package service;

import database.PersonDb;
import domain.Person;

import java.util.List;

public class PersonService {

    private PersonDb personDb;

    public PersonService() {
        this.personDb = new PersonDb();  // PersonDb sınıfını başlatıyoruz
    }

    // Kişi ekleme işlemi
    public void addPerson(Person person) {
        if (person == null || person.getName().isEmpty() || person.getSurname().isEmpty() || person.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Person details cannot be null or empty");
        }
        personDb.addPerson(person);
    }

    // Kişi güncelleme işlemi
    public void updatePerson(Person person) {
        if (person == null || person.getId() <= 0) {
            throw new IllegalArgumentException("Invalid person or ID");
        }
        personDb.updatePerson(person);
    }

    // Kişi silme işlemi
    public void deletePerson(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        personDb.deletePerson(id);
    }

    // Kişi listeleme işlemi
    public List<Person> getAllPersons() {
        return personDb.getAllPersons();
    }

    // Kişi ID'ye göre arama
    public Person getPersonById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return personDb.getPersonById(id);
    }
}
