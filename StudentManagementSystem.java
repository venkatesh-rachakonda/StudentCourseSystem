import java.io.*;
import java.util.*;

public class StudentManagementSystem {
    private static final String STUDENT_FILE = "students.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== STUDENT COURSE MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. Login as Student");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> registerStudent(sc);
                case 2 -> studentLogin(sc);
                case 3 -> adminLogin(sc);
                case 4 -> {
                    System.out.println("Exiting... Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void registerStudent(Scanner sc) throws IOException {
        System.out.print("Enter Student ID: ");
        String id = sc.next();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE, true));
        bw.write(new Student(id, name).toString());
        bw.newLine();
        bw.close();

        System.out.println("Student Registered Successfully!");
    }

    private static void studentLogin(Scanner sc) throws IOException {
        System.out.print("Enter Student ID: ");
        String id = sc.next();

        List<Student> students = loadStudents();
        Student student = null;

        for (Student s : students) {
            if (s.getId().equals(id)) {
                student = s;
                break;
            }
        }

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        while (true) {
            System.out.println("\nWelcome " + student.getName() + "!");
            System.out.println("1. View Available Courses");
            System.out.println("2. Enroll in a Course");
            System.out.println("3. View Enrolled Courses");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int opt = sc.nextInt();

            switch (opt) {
                case 1 -> Admin.listCourses();
                case 2 -> {
                    Admin.listCourses();
                    System.out.print("Enter Course ID to enroll: ");
                    String cid = sc.next();
                    student.enroll(cid);
                    updateStudent(student);
                    System.out.println("Enrolled successfully!");
                }
                case 3 -> {
                    System.out.println("Enrolled Courses:");
                    for (String c : student.getEnrolledCourses()) {
                        System.out.println(c);
                    }
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void adminLogin(Scanner sc) throws IOException {
        // You can set a fixed password or skip it
        System.out.print("Enter Admin Password: ");
        String pwd = sc.next();
        if (!pwd.equals("admin123")) {
            System.out.println("Incorrect password!");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3. Exit Admin");
            System.out.print("Choose an option: ");
            int opt = sc.nextInt();
            switch (opt) {
                case 1 -> Admin.addCourse(sc);
                case 2 -> Admin.listCourses();
                case 3 -> {
                    System.out.println("Exiting Admin...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return students;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            students.add(Student.fromString(line));
        }
        br.close();
        return students;
    }

    private static void updateStudent(Student updatedStudent) throws IOException {
        List<Student> students = loadStudents();
        BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE));
        for (Student s : students) {
            if (s.getId().equals(updatedStudent.getId())) {
                bw.write(updatedStudent.toString());
            } else {
                bw.write(s.toString());
            }
            bw.newLine();
        }
        bw.close();
    }
}