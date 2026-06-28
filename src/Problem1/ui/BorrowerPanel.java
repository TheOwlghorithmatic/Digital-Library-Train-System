package ui;

import javax.swing.*;
import java.awt.*;
import service.LibraryManegmentSystem;
import model.Borrower;

public class BorrowerPanel extends JPanel{

    public BorrowerPanel(LibraryManegmentSystem manager){

        setLayout(new GridLayout(4,2));

        JTextField id=new JTextField();

        JTextField name=new JTextField();

        JCheckBox graduate=new JCheckBox();

        JButton add=new JButton("Add Borrower");

        add(new JLabel("ID"));

        add(id);

        add(new JLabel("Name"));

        add(name);

        add(new JLabel("Graduate"));

        add(graduate);

        add(add);

        add.addActionListener(e->{

            Borrower b=new Borrower(

                    id.getText(),

                    name.getText(),

                    graduate.isSelected()

            );

            manager.addBorrower(b);

            JOptionPane.showMessageDialog(this,"Borrower Added");

        });

    }

}