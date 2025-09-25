package com.example.botscrew;

import com.example.botscrew.Entity.*;
import com.example.botscrew.Repository.*;
import com.example.botscrew.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class BotsCrewApplication implements CommandLineRunner {



    private final UniversityService universityService;
    private final DegreeRepository degreeRepo;
    private final LectorRepository lectorRepo;
    private final DepartmentRepository depRepo;

    public BotsCrewApplication(UniversityService universityService,
                               DegreeRepository degreeRepo,
                               LectorRepository lectorRepo,
                               DepartmentRepository depRepo) {
        this.universityService = universityService;
        this.degreeRepo = degreeRepo;
        this.lectorRepo = lectorRepo;
        this.depRepo = depRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(BotsCrewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Degree assistant = degreeRepo.save(new Degree(null, "assistant"));
        Degree associate = degreeRepo.save(new Degree(null,"associate professor"));
        Degree professor = degreeRepo.save(new Degree(null, "professor"));

        Lector testLector1 = lectorRepo.save(new Lector(null, "Ivan", "Petrenko", professor, 5000.0, Set.of()));
        Lector testLector2 = lectorRepo.save(new Lector(null, "Oksana", "Koval", assistant, 3000.0, Set.of()));
        Lector testLector3 = lectorRepo.save(new Lector(null, "Petro", "Ivanov", associate, 4000.0, Set.of()));

        Department math = new Department(null, "Math", testLector1, Set.of(testLector1, testLector2));
        Department physics = new Department(null, "Physics", testLector3, Set.of(testLector3));
        testLector1.setDepartments(Set.of(math));
        testLector2.setDepartments(Set.of(math));
        testLector3.setDepartments(Set.of(physics));

        lectorRepo.saveAll(List.of(testLector1, testLector2, testLector3));
        math.setLectors(Set.of(testLector1, testLector2));
        physics.setLectors(Set.of(testLector3));

        depRepo.saveAll(List.of(math, physics));


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) break;

            if (input.startsWith("Who is head of department ")) {
                String department = input.replace("Who is head of department ", "");
                System.out.println(universityService.getHeadOfDepartment(department));
            } else if (input.startsWith("Show ") && input.endsWith(" statistics")) {
                String department = input.replace("Show ", "").replace(" statistics", "");
                System.out.println(universityService.getDepartmentStatistics(department));
            } else if (input.startsWith("Show the average salary for the department ")) {
                String department = input.replace("Show the average salary for the department ", "");
                System.out.println(universityService.getAverageSalary(department));
            } else if (input.startsWith("Show count of employee for ")) {
                String department = input.replace("Show count of employee for ", "");
                System.out.println(universityService.getEmployeeCount(department));
            } else if (input.startsWith("Global search by ")) {
                String template = input.replace("Global search by ", "");
                System.out.println(universityService.globalSearch(template));
            } else {
                System.out.println("Unknown command");
            }
        }
    }
}
