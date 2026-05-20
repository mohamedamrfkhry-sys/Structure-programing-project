import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Frame extends JFrame {

    int MAX_STUDENTS = 100;
    int MAX_COURSES = 5;

    int[] studentIDs = new int[100];
    String[] studentNames = new String[100];
    String[] courseNames = new String[5];
    double[][] grades = new double[100][5];

    int studentCount = 0;

    public Frame() {

        int i = 0;
        while (i < MAX_STUDENTS) {
            studentIDs[i] = 0;
            studentNames[i] = "None";
            int j = 0;
            while (j < MAX_COURSES) {
                grades[i][j] = -1;
                j++;
            }
            i++;
        }

        i = 0;
        while (i < MAX_COURSES) {
            courseNames[i] = "Course " + (i + 1);
            i++;
        }

        setTitle("GradeBook System");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 10, 10));

        JLabel title = new JLabel("GRADE-BOOK SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        add(new JLabel(""));

        JButton b1 = new JButton("Student Registration");
        JButton b2 = new JButton("Course Setup");
        JButton b3 = new JButton("Grade Entry");
        JButton b4 = new JButton("Student Report");
        JButton b5 = new JButton("Department Summary");
        JButton b6 = new JButton("Exit");

        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupCourses();
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterGrade();
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayStudentReport();
            }
        });

        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayDepartmentSummary();
            }
        });

        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    void registerStudent() {
        JFrame f = new JFrame("Student Registration");
        f.setSize(400, 250);
        f.setLayout(new GridLayout(5, 2, 10, 10));
        f.setLocationRelativeTo(this);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JLabel result = new JLabel("", SwingConstants.CENTER);

        JButton save = new JButton("Register");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();

                if (name==("")) {
                    result.setText("Name cannot be empty");
                    return;
                }

                if (studentHere(id)) {
                    result.setText("Student ID already exists");
                    return;
                }

                int i = 0;
                while (i < MAX_STUDENTS) {
                    if (studentIDs[i] == 0) {
                        studentIDs[i] = id;
                        studentNames[i] = name;
                        studentCount++;
                        result.setText("Student registered");
                        idField.setText("");
                        nameField.setText("");
                        return;
                    }
                    i++;
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        f.add(new JLabel("Student ID"));
        f.add(idField);
        f.add(new JLabel("Student Name"));
        f.add(nameField);
        f.add(save);
        f.add(cancel);
        f.add(new JLabel(""));
        f.add(result);

        f.setVisible(true);
    }

    void setupCourses() {
        JFrame f = new JFrame("Course Setup");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(7, 2, 10, 10));
        f.setLocationRelativeTo(this);

        JTextField[] fields = new JTextField[5];

        int i = 0;
        while (i < 5) {
            fields[i] = new JTextField(courseNames[i]);
            f.add(new JLabel("Course " + (i + 1)));
            f.add(fields[i]);
            i++;
        }

        JButton save = new JButton("Save");

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int j = 0;
                while (j < 5) {
                    if (!fields[j].getText().trim().equals("")) {
                        courseNames[j] = fields[j].getText().trim();
                    }
                    j++;
                }
                f.dispose();
            }
        });

        f.add(save);
        f.setVisible(true);
    }

    void enterGrade() {
        JFrame f = new JFrame("Grade Entry");
        f.setSize(450, 300);
        f.setLayout(new GridLayout(6, 2, 10, 10));
        f.setLocationRelativeTo(this);

        JTextField idField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField gradeField = new JTextField();
        JLabel result = new JLabel("", SwingConstants.CENTER);

        JButton save = new JButton("Submit Grade");

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(idField.getText().trim());
                int course = Integer.parseInt(courseField.getText().trim());
                double grade = Double.parseDouble(gradeField.getText().trim());

                int index = findStudentIndex(id);

                if (index == -1) {
                    result.setText("Student not found");
                    return;
                }

                if (course < 0 || course >= MAX_COURSES) {
                    result.setText("Invalid course index");
                    return;
                }

                if (grade < 0 || grade > 100) {
                    result.setText("Grade must be 0-100");
                    return;
                }

                grades[index][course] = grade;
                result.setText("Grade recorded");
            }
        });

        f.add(new JLabel("Student ID"));
        f.add(idField);
        f.add(new JLabel("Course Index (0-4)"));
        f.add(courseField);
        f.add(new JLabel("Grade"));
        f.add(gradeField);
        f.add(save);
        f.add(result);

        f.setVisible(true);
    }

    void displayStudentReport() {
        JFrame f = new JFrame("Student Report");
        f.setSize(500, 400);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(this);

        JTextField idField = new JTextField(10);
        JButton search = new JButton("Search");

        JPanel top = new JPanel();
        top.add(new JLabel("Student ID"));
        top.add(idField);
        top.add(search);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(idField.getText().trim());
                int index = findStudentIndex(id);

                if (index == -1) {
                    area.setText("Student not found");
                    return;
                }

                String text = "";
                text += "Student: " + studentNames[index] + "\n";
                text += "-----------------\n";

                double sum = 0;
                int count = 0;

                int i = 0;
                while (i < MAX_COURSES) {
                    if (grades[index][i] == -1) {
                        text += courseNames[i] + " : Not graded\n";
                    } else {
                        text += courseNames[i] + " : " + grades[index][i] + "\n";
                        sum += grades[index][i];
                        count++;
                    }
                    i++;
                }

                if (count > 0) {
                    text += "Average = " + (sum / count);
                } else {
                    text += "Average = N/A";
                }

                area.setText(text);
            }
        });

        f.add(top, BorderLayout.NORTH);
        f.add(new JScrollPane(area), BorderLayout.CENTER);
        f.setVisible(true);
    }

    void displayDepartmentSummary() {
        JFrame f = new JFrame("Department Summary");
        f.setSize(500, 400);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(this);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        int j = 0;
        while (j < MAX_COURSES) {

            double sum = 0;
            int count = 0;

            int i = 0;
            while (i < studentCount) {
                if (grades[i][j] != -1) {
                    sum += grades[i][j];
                    count++;
                }
                i++;
            }

            if (count > 0) {
                area.append(courseNames[j] + " : " + (sum / count) + "\n");
            } else {
                area.append(courseNames[j] + " : N/A\n");
            }

            j++;
        }

        f.add(new JScrollPane(area));
        f.setVisible(true);
    }

    boolean studentHere(int id) {
        int i = 0;
        while (i < MAX_STUDENTS) {
            if (studentIDs[i] == id) {
                return true;
            }
            i++;
        }
        return false;
    }

    int findStudentIndex(int id) {
        int i = 0;
        while (i < MAX_STUDENTS) {
            if (studentIDs[i] == id) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static void main(String[] args) {
        new Frame();
    }
}