package Day4;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Employee{
    private int id;
    private String name;
    private String dept;
    private double salary;

    public Employee(int id, String name, String dept, double salary){
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public double getSalary(){
        return salary;
    }

    @Override
    public String toString(){
        return id + " | " + name + " | " + dept + " | $ " + salary ;
    }
}


public class CsvReader {
    public static void main(String [] args){
        String FileName = "C:\\Users\\nithish.kumar\\Downloads\\employes.csv";

        try (Stream <String> lines = Files.lines(Paths.get(FileName))){
            List <Employee> employees = lines
                .skip(1)
                .map(line -> line.split(","))
                .map(data -> new Employee(
                    Integer.parseInt(data[0]),
                    data[1],
                    data[2],
                    Double.parseDouble(data[3])

                ))
                .filter(emp -> emp.getSalary() > 50000)
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .collect(Collectors.toList());
            employees.forEach(System.out::println);
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
