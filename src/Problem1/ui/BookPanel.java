package ui;

import javax.swing.*;
import java.awt.*;
import service.LibraryManegmentSystem;
import model.Book;

public class BookPanel extends JPanel {

    public BookPanel(LibraryManegmentSystem manager){

        setLayout(new GridLayout(5,2));

        JTextField isbn=new JTextField();

        JTextField title=new JTextField();

        JTextField author=new JTextField();

        JTextField copies=new JTextField();

        JButton add=new JButton("Add");

        JButton delete=new JButton("Delete");

        add(new JLabel("ISBN"));

        add(isbn);

        add(new JLabel("Title"));

        add(title);

        add(new JLabel("Author"));

        add(author);

        add(new JLabel("Copies"));

        add(copies);

        add(add);

        add(delete);

        add.addActionListener(e->{

            Book b=new Book(

                    isbn.getText(),

                    title.getText(),

                    author.getText(),

                    Integer.parseInt(copies.getText())

            );

            manager.addBook(b);

            JOptionPane.showMessageDialog(this,"Book Added");

        });

        delete.addActionListener(e->{

            manager.removeBook(isbn.getText());

            JOptionPane.showMessageDialog(this,"Book Deleted");

        });

    }

}