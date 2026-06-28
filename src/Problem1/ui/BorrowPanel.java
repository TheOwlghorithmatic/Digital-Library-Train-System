package ui;

import javax.swing.*;

import java.awt.*;

import java.time.LocalDate;

import service.LibraryManegmentSystem;

public class BorrowPanel extends JPanel{

    public BorrowPanel(LibraryManegmentSystem manager){

        setLayout(new GridLayout(6,2));

        JTextField borrowerId=new JTextField();

        JTextField isbn=new JTextField();

        JTextField borrowDate=new JTextField("2026-01-01");

        JTextField dueDate=new JTextField("2026-01-15");

        JButton borrow=new JButton("Borrow");

        JButton ret=new JButton("Return");

        add(new JLabel("Borrower ID"));

        add(borrowerId);

        add(new JLabel("ISBN"));

        add(isbn);

        add(new JLabel("Borrow Date"));

        add(borrowDate);

        add(new JLabel("Due Date"));

        add(dueDate);

        add(borrow);

        add(ret);

        borrow.addActionListener(e->{

            boolean ok=manager.borrowBook(

                    borrowerId.getText(),

                    isbn.getText(),

                    LocalDate.parse(borrowDate.getText()),

                    LocalDate.parse(dueDate.getText())

            );

            JOptionPane.showMessageDialog(this,

                    ok?"Borrowed":"Failed");

        });

        ret.addActionListener(e->{

            boolean ok=manager.returnBook(

                    borrowerId.getText(),

                    isbn.getText()

            );

            JOptionPane.showMessageDialog(this,

                    ok?"Returned":"Failed");

        });

    }

}