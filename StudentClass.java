class Student {
    private String name;
    private int age;

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String newName) {
        name = newName;
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Setter for age (with validation)
    public void setAge(int newAge) {
        if (newAge > 0) {
            age = newAge;
        } else {
            System.out.println("Age must be positive!");
        }
    }
}

public class StudentClass {
    public static void main(String[] args) {
        Student student = new Student();

        // Using setter methods
        student.setName("Nithish");
        student.setAge(21);

        // Using getter methods
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Age: " + student.getAge());
    }
}
