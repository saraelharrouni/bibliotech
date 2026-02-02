package fr.amu.univ.miage.m1.glq.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("La classe de gestion de paiement des salariés")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmployeeManagerTest {

    @InjectMocks
    private EmployeeManager employeeManager;

    @Mock
    private EmployeeRepository mockedEmployeeRepository;
    @Mock
    private BankService mockedBankService;

    @Test
    void devrait_retourner_zero_quand_il_n_y_a_pas_de_salaries() {
        when(mockedEmployeeRepository.findAll()).thenReturn(emptyList());
        assertThat(employeeManager.payEmployees()).isZero();
    }

    @Test
    void devrait_retourner_un_quand_il_y_a_un_seul_salarie() {
        List<Employee> employees = List.of(new Employee("1", 3000));
        when(mockedEmployeeRepository.findAll()).thenReturn(employees);

        assertThat(employeeManager.payEmployees()).isOne();
        verify(mockedBankService).pay("1", 3000);
    }

    @Test
    void devrait_retourner_deux_quand_il_y_a_deux_salaries() {
        List<Employee> employees = List.of(new Employee("1", 3000), new Employee("2", 5000));
        when(mockedEmployeeRepository.findAll()).thenReturn(employees);

        assertThat(employeeManager.payEmployees()).isEqualTo(2);
        verify(mockedBankService).pay("1", 3000);
        verify(mockedBankService).pay("2", 5000);
    }
}