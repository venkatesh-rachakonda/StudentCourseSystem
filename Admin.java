import java.io.*;
import java.util.*;

public class Admin {
    private static final String COURSE_FILE = "courses.txt";

    public static void addCourse(Scanner sc) throws IOException {
        System.out.print("Enter Course ID: ");
        String id = sc.next();
        sc.nextLine();  // Consume newline
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();

        BufferedWriter bw = new BufferedWriter(new FileWriter(COURSE_FILE, true));
        bw.write(id + "," + name);
        bw.newLine();
        bw.close();

        System.out.println("Course added successfully!");
    }

    public static List<Course> getCourses() throws IOException {
        List<Course> courses = new ArrayList<>();
        File file = new File(COURSE_FILE);
        if (!file.exists()) return courses;

        BufferedReader br = new BufferedReader(new FileReader(COURSE_FILE));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            courses.add(new Course(parts[0], parts[1]));
        }
        br.close();
        return courses;
    }

    public static void listCourses() throws IOException {
        List<Course> courses = getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("Available Courses:");
            for (Course c : courses) {
                System.out.println(c);
            }
        }
    }
}