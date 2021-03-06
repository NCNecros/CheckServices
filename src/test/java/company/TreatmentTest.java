package company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
@ContextConfiguration(classes = AppConfig.class)
public class TreatmentTest extends AbstractTestNGSpringContextTests {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final Logger logger = LoggerFactory.getLogger(TreatmentTest.class);
    private Treatment treatment;

    @BeforeMethod
    public void setUp() throws Exception {
        Human human = new Human("1111",
                "Иванов",
                "Иван",
                "Иванович",
                dateFormat.parse("01.01.2015"));
        treatment = applicationContext.getBean(Treatment.class);
        treatment.setParent(human);
    }

    @Test
    public void TestInvalidDateOfService() throws ParseException {
        treatment.getUslugi().put(1d, new Service("",
                dateFormat.parse("01.01.2015"),
                null,
                treatment));
        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("01.01.2015"));
        logger.debug(treatment.toString());
//        assertEquals(treatment.treatmentChecker.checkUslugi(treatment).get(0), "1111\tИванов Иван Иванович\t01.01.2015\tсодержит обращение с незакрытыми мероприятиями");

    }

    @Test
    public void TestMissedGinecologServices() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.001.002",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1, treatment.toString());
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) отсутствует обращение - врач-акушер-гинеколог");

    }

    @Test
    public void TestCorrectTreatmentWithGinecologServices() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.001.001",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.001.019",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 0);
    }

    @Test
    public void TestRealCorrectTreatmentWithGinecologServices() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("06.03.2015"),
                dateFormat.parse("06.03.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.001.001",
                dateFormat.parse("10.03.2015"),
                dateFormat.parse("10.03.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.001.019",
                dateFormat.parse("06.03.2015"),
                dateFormat.parse("06.03.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("06.03.2015"));
        treatment.setDato(dateFormat.parse("10.03.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 0);
    }

    @Test
    public void TestOneTreatmentWithIncorrectDatnOfService() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("07.03.2015"),
                dateFormat.parse("07.03.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.001.001",
                dateFormat.parse("10.03.2015"),
                dateFormat.parse("10.03.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.001.019",
                dateFormat.parse("10.03.2015"),
                dateFormat.parse("10.03.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("06.03.2015"));
        treatment.setDato(dateFormat.parse("10.03.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1);
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(06.03.2015) нет услуги совпадающей с датой начала лечения");
    }

    @Test
    public void TestOneTreatmentWithIncorrectDatoOfService() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("06.03.2015"),
                dateFormat.parse("06.03.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("06.03.2015"));
        treatment.setDato(dateFormat.parse("10.03.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1);
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(06.03.2015) нет услуги совпадающей с датой окончания лечения");
    }

    @Test
    public void TestOneServiceOfGinecologAndRedundantService() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.001.001",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.001.019",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1);
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) лишнее обращение - врач-акушер-гинеколог");
    }


    @Test
    public void TestMissedNevrologServices() throws ParseException {
            treatment.getUslugi().put(1d, new Service("B01.023.010",
                    dateFormat.parse("01.01.2015"),
                    dateFormat.parse("01.01.2015"),
                    treatment));

            treatment.getUslugi().put(3d, new Service("B01.023.010",
                    dateFormat.parse("02.01.2015"),
                    dateFormat.parse("02.01.2015"),
                    treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//            List<String> strings = treatment.checkUslugi();
//            assertEquals(strings.size(), 1, treatment.toString());
//            assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) отсутствует обращение - врач-невролог");
    }

    @Test
    public void TestCorrectTreatmentWithNevrologServices() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.023.010",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.023.012",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 0);
    }

    @Test
    public void TestOneServiceOfNevrologAndRedundantService() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(3d, new Service("B01.023.012",
                dateFormat.parse("02.01.2015"),
                dateFormat.parse("02.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("02.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1);
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) лишнее обращение - врач-невролог");
    }
    @Test
    public void TestForServiceDuplicates() throws ParseException {
        treatment.getUslugi().put(1d, new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.getUslugi().put(2d, new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment));

        treatment.setDatn(dateFormat.parse("01.01.2015"));
        treatment.setDato(dateFormat.parse("01.01.2015"));
//        List<String> strings = treatment.checkUslugi();
//        assertTrue(strings.contains("1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) содержит дубликаты услуг"));
    }

    @Test
    public void TestEquality() throws ParseException {
        Service service1 = new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment);

        Service service2 = new Service("B01.023.010",
                dateFormat.parse("01.01.2015"),
                dateFormat.parse("01.01.2015"),
                treatment);

        List<Service> services = new ArrayList<>();
        services.add(service1);
        services.add(service2);

        assertEquals(Collections.frequency(services, service1), 2);
        assertEquals(service1, service2);
    }

}