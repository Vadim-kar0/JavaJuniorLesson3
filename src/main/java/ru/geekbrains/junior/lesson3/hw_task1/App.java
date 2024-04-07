package ru.geekbrains.junior.lesson3.hw_task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.geekbrains.junior.lesson3.task2.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static final String FILE_JSON = "student.json";
    public static final String FILE_BIN = "student.bin";
    public static final String FILE_XML = "student.xml";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();


    public static void main(String[] args) {
        Student student = new Student("Student1", 10,100);

        System.out.println("Имя: " + student.getName());
        System.out.println("Возраст: " + student.getAge());
        System.out.println("Средний балл: " + student.getGpa());
        System.out.println();

        saveStudentToFile(FILE_JSON,student);
        saveStudentToFile(FILE_BIN,student);
        saveStudentToFile(FILE_XML,student);

        student = loadTasksFromFile(FILE_JSON);
        System.out.println("Student from JSON");
        System.out.println("Имя: " + student.getName());
        System.out.println("Возраст: " + student.getAge());
        System.out.println("Средний балл: " + student.getGpa());
        System.out.println();
        System.out.println("Student from BIN");
        student = loadTasksFromFile(FILE_BIN);
        System.out.println("Имя: " + student.getName());
        System.out.println("Возраст: " + student.getAge());
        System.out.println("Средний балл: " + student.getGpa());
        System.out.println();
        student = loadTasksFromFile(FILE_XML);
        System.out.println("Student from XML");
        System.out.println("Имя: " + student.getName());
        System.out.println("Возраст: " + student.getAge());
        System.out.println("Средний балл: " + student.getGpa());
        System.out.println();

    }


    public static void saveStudentToFile(String fileName, Student student) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), student);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(student);
                }
            }
            else if (fileName.endsWith(".xml")) {
                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                xmlMapper.writeValue(new File(fileName), student);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Student loadTasksFromFile(String fileName){
        Student student = new Student();
        File file = new File(fileName);
        try {
            if (file.exists()) {
                if (fileName.endsWith(".json")) {
                    student = objectMapper.readValue(file, objectMapper.getTypeFactory().constructType(Student.class));
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        student = (Student) ois.readObject();
                    }
                }
                else if (fileName.endsWith(".xml")) {
                    student = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructType(Student.class));
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return student;
    }

}
