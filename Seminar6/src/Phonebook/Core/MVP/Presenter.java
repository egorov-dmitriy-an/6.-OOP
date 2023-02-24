package Phonebook.Core.MVP;

import Phonebook.Core.Infrastructure.Phonebook;
import Phonebook.Core.Models.Contact;

public class Presenter {

    private Model model;
    private ConsoleView view;

    public Presenter(ConsoleView view, String pathDb) {
        this.view = view;
        model = new Model(pathDb);
    }

    public void LoadFromFile() {
        model.load();

        if (model.currentBook().count() > 0) {
            model.setCurrentIndex(0);
            var contact = model.currentContact();

            view.setFirstName(contact.firstName);
            view.setLastName(contact.lastName);
            view.setPhone(contact.phone);
        }
    }

    public void showAll() {
        Phonebook book = model.currentBook();
        for (int i = 0; i < book.count(); i++) {
            Contact contact = book.getContact(i);
            view.setFirstName(contact.firstName);
            view.setLastName(contact.lastName);
            view.setPhone(contact.phone);
            System.out.println("--------------------");
        }
    }

    public void add() {
        model.currentBook().add(
                new Contact(view.getFirstName(), view.getLastName(), view.getPhone()));
    }

    public void remove() {
        Contact contact = new Contact(view.getFirstName(), view.getLastName(), view.getPhone());
        model.currentBook().remove(contact);

        if (model.currentBook().count() < 1) {
            model.setCurrentIndex(-1);
            view.setFirstName("");
            view.setLastName("");
            view.setPhone("");
        } else {
            model.setCurrentIndex(model.getCurrentIndex() - 1);
            if (model.getCurrentIndex() < 0)
                model.setCurrentIndex(0);

            Contact temp = model.currentContact();
            view.setFirstName(temp.firstName);
            view.setLastName(temp.lastName);
            view.setPhone(temp.phone);
        }
    }

    public void next() {
        if (model.currentBook().count() > 0) {
            if (model.getCurrentIndex() + 1 < model.currentBook().count()) {
                model.setCurrentIndex(model.getCurrentIndex() + 1);
                Contact contact = model.currentContact();
                view.setFirstName(contact.firstName);
                view.setLastName(contact.lastName);
                view.setPhone(contact.phone);
            }
        }
    }

    public void prev() {
        if (model.currentBook().count() > 0) {
            if (model.getCurrentIndex() - 1 > -1) {
                model.setCurrentIndex(model.getCurrentIndex() - 1);
                Contact contact = model.currentContact();
                view.setFirstName(contact.firstName);
                view.setLastName(contact.lastName);
                view.setPhone(contact.phone);
            }
        }
    }
}
