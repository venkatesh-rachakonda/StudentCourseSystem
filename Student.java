import java.util.ArrayList;

public class Student {
    private String name;
    private String id;
    private ArrayList<String> enrolledCourses;

    public Student(String id, String name) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void enroll(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + String.join(";", enrolledCourses);
    }

    public static Student fromString(String data) {
        String[] parts = data.split(",");
        Student s = new Student(parts[0], parts[1]);
        if (parts.length > 2) {
            for (String c : parts[2].split(";")) {
                s.enroll(c);
            }
        }
        return s;
    }
}