package ui;

import javax.swing.*;

import service.LibraryManegmentSystem;

public class ReportPanel extends JPanel{

    public ReportPanel(LibraryManegmentSystem manager){

        JButton report=new JButton("Generate Report");

        add(report);

        report.addActionListener(e->{

            manager.generateReport();

            JOptionPane.showMessageDialog(this,

                    "Report Generated");

        });

    }

}