 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.io.*;

public class Main extends JFrame {
    private JTextField nameField, rollField, emailField;
    private JComboBox<String> dept;
    private JCheckBox extraCurricular;
    private JRadioButton firstYear, secondYear, thirdYear, fourthYear;
    private JButton submitBtn, resetBtn, summaryBtn;
    private ButtonGroup yearGroup;
    public Main() {
        setTitle("Student Information Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 5, 5));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        add(rollField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Department:"));
        String[] departments = {"CS", "IT", "EE", "ME"};
        dept = new JComboBox<>(departments);
        add(dept);

        add(new JLabel("Extracurricular Activities:"));
        extraCurricular = new JCheckBox("Participates");
        add(extraCurricular);

        add(new JLabel("Year:"));
        JPanel yearPanel = new JPanel(new FlowLayout());
        firstYear = new JRadioButton("First Year");
        secondYear = new JRadioButton("Second Year");
        thirdYear = new JRadioButton("Third Year");
        fourthYear = new JRadioButton("Fourth Year");

        yearGroup = new ButtonGroup();
        yearGroup.add(firstYear);
        yearGroup.add(secondYear);
        yearGroup.add(thirdYear);
        yearGroup.add(fourthYear);

        yearPanel.add(firstYear);
        yearPanel.add(secondYear);
        yearPanel.add(thirdYear);
        yearPanel.add(fourthYear);
        add(yearPanel);

        submitBtn = new JButton("Submit");
        resetBtn = new JButton("Reset");
        summaryBtn = new JButton("Show Summary");

        add(submitBtn);
        add(resetBtn);
        add(summaryBtn);

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        summaryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSummary();
            }
        });

        setVisible(true);
    }

    private void submitForm() {
        String name = nameField.getText().trim();
        String rollNo = rollField.getText().trim();
        String email = emailField.getText().trim();
        String dept = (String) this.dept.getSelectedItem();
        String year = getSelectedYear();
        String activities = extraCurricular.isSelected() ? "Yes" : "No";

        if (name.isEmpty() || rollNo.isEmpty() || email.isEmpty() || year == null) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true))) {
            writer.write("Name: " + name + "\n");
            writer.write("Roll Number: " + rollNo + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Department: " + dept + "\n");
            writer.write("Year: " + year + "\n");
            writer.write("Extracurricular Activities: " + activities + "\n");
            writer.write("---------------------------------------\n");
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
            resetForm();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        nameField.setText("");
        rollField.setText("");
        emailField.setText("");
        dept.setSelectedIndex(0);
        extraCurricular.setSelected(false);
        yearGroup.clearSelection();
    }

    private void showSummary() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String email = emailField.getText().trim();
        String dept = (String) this.dept.getSelectedItem();
        String year = getSelectedYear();
        String activities = extraCurricular.isSelected() ? "Yes" : "No";

        if (name.isEmpty() || roll.isEmpty() || email.isEmpty() || year == null) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields before showing summary!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String summary = "Name: " + name + "\n"
                + "Roll Number: " + roll + "\n"
                + "Email: " + email + "\n"
                + "Department: " + dept + "\n"
                + "Year: " + year + "\n"
                + "Extracurricular Activities: " + activities;

        JOptionPane.showMessageDialog(this, summary, "Student Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getSelectedYear() {
        if (firstYear.isSelected()) return "First Year";
        if (secondYear.isSelected()) return "Second Year";
        if (thirdYear.isSelected()) return "Third Year";
        if (fourthYear.isSelected()) return "Fourth Year";
        return null;
    }

    public static void main(String[] args) {
        new Main();
    }
}
