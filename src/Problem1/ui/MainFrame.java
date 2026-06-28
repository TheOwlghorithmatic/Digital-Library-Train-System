package ui;

import javax.swing.*;
import service.LibraryManegmentSystem;;

public class MainFrame extends JFrame {
    

    public MainFrame(LibraryManegmentSystem manager) {

        setTitle("Library Management System");

        setSize(700,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Books", new BookPanel(manager));

        tabs.add("Borrowers", new BorrowerPanel(manager));

        tabs.add("Borrow / Return", new BorrowPanel(manager));

        tabs.add("Report", new ReportPanel(manager));

        add(tabs);

    }
    

}