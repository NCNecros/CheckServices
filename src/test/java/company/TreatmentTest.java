package company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
@ContextConfiguration(classes = AppConfig.class )
public class TreatmentTest extends AbstractTestNGSpringContextTests {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Treatment treatment;
    @Resource
    private List<String> ginecologServices;
    @Resource
    private List<String> pediatrServices;
    @Resource
    private List<String> nevrologServices;
    @Resource
    private List<String> lorServices;
    @Resource
    private List<String> oftalmologServices;
    @Resource
    private List<String> terapevtServices;

    @BeforeMethod
    public void setUp() throws Exception {
        Human human = new Human();
        human.setFio("Иванов");
        human.setIma("Иван");
        human.setOtch("Иванович");
        human.setIsti("1111");
        human.setDatr(dateFormat.parse("01.01.2015"));
        treatment = new Treatment();
        treatment.setParent(human);
    }

    @Test
    public void TestInvalidDateOfService() {
        try {
            Service service = new Service();
            service.setDatn(dateFormat.parse("01.01.2015"));
            service.setDato(null);
            service.setParent(treatment);
            treatment.getUslugi().put(1d, service);
            assertEquals(treatment.checkUslugi().get(0), "1111\tИванов Иван Иванович\t01.01.2015\tсодержит обращение с незакрытыми мероприятиями");
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void TestMissedGinecologServices() throws ParseException {
        Service service;
        service = new Service();
        service.setDatn(dateFormat.parse("01.01.2015"));
        service.setDato(dateFormat.parse("01.01.2015"));
        service.setParent(treatment);
        service.setKusl(ginecologServices.get(0));
        treatment.getUslugi().put(1d, service);

        service = new Service();
        service.setDatn(dateFormat.parse("02.01.2015"));
        service.setDato(dateFormat.parse("02.01.2015"));
        service.setParent(treatment);
        service.setKusl(ginecologServices.get(0));
        treatment.getUslugi().put(2d, service);
        List<String> strings = treatment.checkUslugi();
        assertEquals(strings.size(), 1);
        assertEquals(strings.get(0),"1111\tИванов Иван Иванович\t01.01.2015\tсодержит обращение с незакрытыми мероприятиями");

    }
}