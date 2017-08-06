import dream.development.hibernate.controllers.EmployeeController;
import dream.development.hibernate.dao.interfaces.EmployeeDao;
import dream.development.hibernate.model.Employee;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

/**
 * Employee Controller Test
 * Created by Администратор on 30.07.2017.
 */
public class EmployeeControllerTest {

    @Mock
    private EmployeeDao dao;

    @InjectMocks
    private EmployeeController controller = new EmployeeController();

    @BeforeMethod
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testName() throws Exception{
        when(dao.getAll()).thenReturn(Stream.generate(getEmployee()).limit(10).collect(Collectors.toList()));
    }

    private Supplier<Employee> getEmployee() {
        Employee employee = new Employee();
        return Employee::new;
    }
}
