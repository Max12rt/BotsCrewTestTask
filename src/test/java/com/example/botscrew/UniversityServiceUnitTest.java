package com.example.botscrew;

import com.example.botscrew.Entity.Degree;
import com.example.botscrew.Entity.Department;
import com.example.botscrew.Entity.Lector;
import com.example.botscrew.Repository.DepartmentRepository;
import com.example.botscrew.Repository.LectorRepository;
import com.example.botscrew.Service.UniversityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UniversityServiceUnitTest {

    private DepartmentRepository departmentRepository;
    private LectorRepository lectorRepository;
    private UniversityService universityService;

    Degree assistant = new Degree(null, "assistant");
    Degree associate = new Degree(null,"associate professor");
    Degree professor = new Degree(null, "professor");

    @BeforeEach
    void setUp() {
        departmentRepository = Mockito.mock(DepartmentRepository.class);
        lectorRepository = Mockito.mock(LectorRepository.class);
        universityService = new UniversityService(departmentRepository, lectorRepository);
    }

    @Test
    void testGetHeadOfDepartment() {

        Lector head = new Lector(1L, "Ivan", "Petrenko", professor, 5000.0, Set.of());
        Department department = new Department(1L, "Math", head, Set.of());

        when(departmentRepository.findByName("Math")).thenReturn(Optional.of(department));

        String result = universityService.getHeadOfDepartment("Math");

        assertThat(result).isEqualTo("Head of Math department is Ivan Petrenko");
    }

    @Test
    void testDepartmentStatistics() {

        Lector l1 = new Lector(1L, "Oleh", "Ivanov", assistant, 2000.0, Set.of());
        Lector l2 = new Lector(2L, "Petro", "Shevchenko", associate, 3000.0, Set.of());
        Lector l3 = new Lector(3L, "Iryna", "Koval", professor, 4000.0, Set.of());

        when(lectorRepository.findByDepartmentsName("Math"))
                .thenReturn(List.of(l1, l2, l3));

        String result = universityService.getDepartmentStatistics("Math");

        assertThat(result).contains("assistants - 1")
                .contains("associate professors - 1")
                .contains("professors - 1");
    }

    @Test
    void testAverageSalary() {

        Lector l1 = new Lector(1L, "Oleh", "Ivanov", assistant , 2000.0, Set.of());
        Lector l2 = new Lector(2L, "Petro", "Shevchenko", associate, 4000.0, Set.of());

        when(lectorRepository.findByDepartmentsName("Math")).thenReturn(List.of(l1, l2));

        String result = universityService.getAverageSalary("Math");

        assertThat(result).isEqualTo("The average salary of Math is 3000.0");
    }

    @Test
    void testEmployeeCount() {
        when(lectorRepository.findByDepartmentsName("Math"))
                .thenReturn(List.of(new Lector(), new Lector(), new Lector()));

        String result = universityService.getEmployeeCount("Math");

        assertThat(result).isEqualTo("3");
    }

    @Test
    void testGlobalSearch() {

        Lector l1 = new Lector(1L, "Ivan", "Petrenko", professor, 5000.0, Set.of());
        Lector l2 = new Lector(2L, "Petro", "Ivanov", assistant, 3000.0, Set.of());

        when(lectorRepository.globalSearch("van"))
                .thenReturn(List.of(l1, l2));

        String result = universityService.globalSearch("van");

        assertThat(result).contains("Ivan Petrenko", "Petro Ivanov");
    }
}

