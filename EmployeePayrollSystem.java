import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// Employee class
class Employee
{
    String id, name, department;
    double basicSalary;

    // Default constructor
    Employee()
    {
        id = "";
        name = "";
        department = "";
        basicSalary = 0;
    }

    // Parameterized constructor
    Employee(String id, String name,
             String department, double basicSalary)
    {
        this.id = id;
        this.name = name;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Copy constructor
    Employee(Employee e)
    {
        id = e.id;
        name = e.name;
        department = e.department;
        basicSalary = e.basicSalary;
    }
}

public class EmployeePayrollSystem extends JFrame
{
    JTextField idField, nameField, salaryField;
    JComboBox<String> departmentBox;
    JTextArea output;

    ArrayList<Employee> employees = new ArrayList<Employee>();

    EmployeePayrollSystem()
    {
        setTitle("Employee Payroll Management System");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 244, 250));

        // Heading
        JLabel heading = new JLabel(
            "EMPLOYEE PAYROLL SYSTEM", JLabel.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setOpaque(true);
        heading.setBackground(new Color(25, 45, 85));
        heading.setPreferredSize(new Dimension(900, 75));

        add(heading, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 244, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            10, 25, 15, 25));

        add(mainPanel, BorderLayout.CENTER);

        // Top panel contains form and buttons
        JPanel topPanel = new JPanel(new BorderLayout(10, 15));
        topPanel.setBackground(new Color(240, 244, 250));

        // Employee form
        JPanel form = new JPanel(new GridLayout(4, 2, 12, 12));
        form.setBackground(Color.WHITE);

        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 220, 235)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        Font labelFont = new Font("Arial", Font.BOLD, 15);

        JLabel idLabel = new JLabel("Employee ID:");
        JLabel nameLabel = new JLabel("Employee Name:");
        JLabel salaryLabel = new JLabel("Basic Salary:");
        JLabel deptLabel = new JLabel("Department:");

        idLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        salaryLabel.setFont(labelFont);
        deptLabel.setFont(labelFont);

        idField = new JTextField();
        nameField = new JTextField();
        salaryField = new JTextField();

        departmentBox = new JComboBox<String>(new String[] {
            "IT", "HR", "Finance", "Marketing", "Sales"
        });

        // Basic Salary before Department
        form.add(idLabel);
        form.add(idField);

        form.add(nameLabel);
        form.add(nameField);

        form.add(salaryLabel);
        form.add(salaryField);

        form.add(deptLabel);
        form.add(departmentBox);

        topPanel.add(form, BorderLayout.CENTER);

        // Only two buttons
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 30, 8));

        buttonPanel.setBackground(new Color(240, 244, 250));

        JButton saveButton = new JButton("SAVE EMPLOYEE");
        JButton getButton = new JButton("GET DATA");

        saveButton.setPreferredSize(new Dimension(220, 45));
        getButton.setPreferredSize(new Dimension(220, 45));

        saveButton.setFont(new Font("Arial", Font.BOLD, 15));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(45, 100, 190));
        saveButton.setFocusPainted(false);
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);

        getButton.setFont(new Font("Arial", Font.BOLD, 15));
        getButton.setForeground(Color.WHITE);
        getButton.setBackground(new Color(20, 145, 110));
        getButton.setFocusPainted(false);
        getButton.setOpaque(true);
        getButton.setBorderPainted(false);

        buttonPanel.add(saveButton);
        buttonPanel.add(getButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Large Employee Records section
        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setBackground(new Color(250, 252, 255));
        output.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(190, 205, 225)),
            "Employee Records",
            0, 0,
            new Font("Arial", Font.BOLD, 16)));

        // CENTER gives records most of the remaining space
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button events
        saveButton.addActionListener(e -> saveEmployee());
        getButton.addActionListener(e -> getData());

        setVisible(true);
    }

    // Save employee details
    void saveEmployee()
    {
        try
        {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String department = departmentBox.getSelectedItem().toString();

            if (id.isEmpty() || name.isEmpty()
                || salaryField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all the fields!");
                return;
            }

            if (id.contains("|") || name.contains("|")
                || id.contains("\n") || name.contains("\n"))
            {
                JOptionPane.showMessageDialog(this,
                    "Employee ID and name contain invalid characters!");
                return;
            }

            double basicSalary = Double.parseDouble(
                salaryField.getText().trim());

            if (basicSalary <= 0)
            {
                JOptionPane.showMessageDialog(this,
                    "Salary must be greater than zero!");
                return;
            }

            double allowance = basicSalary * 0.10;
            double deduction = basicSalary * 0.05;
            double netSalary = basicSalary + allowance - deduction;

            Employee emp = new Employee(
                id, name, department, basicSalary);

            employees.add(emp);

            BufferedWriter bw = new BufferedWriter(
                new FileWriter("employees.txt", true));

            bw.write(id + " | " + name + " | "  + department + " | "
                + basicSalary + " | " + allowance + " | " 
                + deduction + " | " + netSalary);

            bw.newLine();
            bw.close();

            JOptionPane.showMessageDialog(this,
                "Employee saved successfully!");

            idField.setText("");
            nameField.setText("");
            salaryField.setText("");
            departmentBox.setSelectedIndex(0);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid numeric salary!");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Error saving employee data!");
        }
    }

    // Get employee details from text file
    void getData()
    {
        output.setText("");

        try
        {
            BufferedReader br = new BufferedReader(
                new FileReader("employees.txt"));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null)
            {
                String[] data = line.split("\\|", -1);

                if (data.length == 7)
                {
                    found = true;

                    output.append(
                        "Employee ID : " + data[0] + "\n" +
                        "Name        : " + data[1] + "\n" +
                        "Department  : " + data[2] + "\n" +
                        "Basic Salary: " + data[3] + "\n" +
                        "Allowance   : " + data[4] + "\n" +
                        "Deduction   : " + data[5] + "\n" +
                        "Net Salary  : " + data[6] + "\n" +
                        "--------------------------------------------------\n\n");
                }
            }

            br.close();

            if (!found)
            {
                output.setText("No employee records found.");
            }
        }
        catch (FileNotFoundException ex)
        {
            output.setText(
                "No data file found. Save an employee first.");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Error reading employee data!");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
            () -> new EmployeePayrollSystem());
    }
}