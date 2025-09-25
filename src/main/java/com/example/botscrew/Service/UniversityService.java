package com.example.botscrew.Service;

import com.example.botscrew.Entity.Lector;
import com.example.botscrew.Repository.DegreeRepository;
import com.example.botscrew.Repository.DepartmentRepository;
import com.example.botscrew.Repository.LectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final DepartmentRepository departmentRepository;
    private final LectorRepository lectorRepository;

    public UniversityService(DepartmentRepository departmentRepository,
                             LectorRepository lectorRepository){
        this.departmentRepository = departmentRepository;
        this.lectorRepository = lectorRepository;
    }

    public String getHeadOfDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(d -> "Head of " + departmentName + " department is "
                        + d.getHead().getFirstName() + " " + d.getHead().getLastName())
                .orElse("Department not found");
    }

    public String getDepartmentStatistics(String departmentName) {
        List<Lector> lectors = lectorRepository.findByDepartmentsName(departmentName);

        Map<String, Long> count = lectors.stream()
                .collect(Collectors.groupingBy(l -> l.getDegree().getName(), Collectors.counting()));

        return "assistants - " + count.getOrDefault("assistant", 0L) + ". "
                + "associate professors - " + count.getOrDefault("associate professor", 0L) + ". "
                + "professors - " + count.getOrDefault("professor", 0L);
    }

    public String getAverageSalary(String departmentName) {
        List<Lector> lectors = lectorRepository.findByDepartmentsName(departmentName);
        double avg = lectors.stream().mapToDouble(Lector::getSalary).average().orElse(0.0);
        return "The average salary of " + departmentName + " is " + avg;
    }

    public String getEmployeeCount(String departmentName) {
        List<Lector> lectors = lectorRepository.findByDepartmentsName(departmentName);
        return String.valueOf(lectors.size());
    }

    public String globalSearch(String template) {
        List<Lector> lectors = lectorRepository.globalSearch(template);
        return lectors.stream()
                .map(l -> l.getFirstName() + " " + l.getLastName())
                .collect(Collectors.joining(", "));
    }
}
