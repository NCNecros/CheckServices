package company;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
@ContextConfiguration(classes = AppConfig.class)
public class TreatmentTest extends AbstractTestNGSpringContextTests {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
        assertEquals(treatment.checkUslugi().get(0), "1111\tИванов Иван Иванович\t01.01.2015\tсодержит обращение с незакрытыми мероприятиями");

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
        List<String> strings = treatment.checkUslugi();
        assertEquals(strings.size(), 1, treatment.toString());
        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(01.01.2015) отсутствует обращение - врач-акушер-гинеколог");

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

        List<String> strings = treatment.checkUslugi();
        assertEquals(strings.size(), 0);
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
        List<String> strings = treatment.checkUslugi();
        assertEquals(strings.size(), 0);
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

        treatment.setDatn(dateFormat.parse("06.03.2015"));
        treatment.setDato(dateFormat.parse("10.03.2015"));
        List<String> strings = treatment.checkUslugi();
        assertEquals(strings.size(), 1);
        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t(06.03.2015) лишнее обращение - врач-акушер-гинеколог");
    }
//
//
//    @Test
//    public void TestMissedNevrologServices() throws ParseException {
//        for (String ser : nevrologServices) {
//            treatment.getUslugi().put(1d, new Service(ser,
//                    dateFormat.parse("01.01.2015"),
//                    dateFormat.parse("01.01.2015"),
//                    treatment));
//
//            treatment.getUslugi().put(3d, new Service(ser,
//                    dateFormat.parse("02.01.2015"),
//                    dateFormat.parse("02.01.2015"),
//                    treatment));
//
//            List<String> strings = treatment.checkUslugi();
//            assertEquals(strings.size(), 1, treatment.toString());
//            assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t добавить обращение к врачу-неврологу.");
//        }
//    }
//
//    @Test
//    public void TestCorrectTreatmentWithNevrologServices() throws ParseException {
//        treatment.getUslugi().put(1d, new Service(ser,
//                dateFormat.parse("01.01.2015"),
//                dateFormat.parse("01.01.2015"),
//                treatment));
//
//        treatment.getUslugi().put(2d, new Service(ser,
//                dateFormat.parse("02.01.2015"),
//                dateFormat.parse("02.01.2015"),
//                treatment));
//
//        treatment.getUslugi().put(3d, new Service(treatment.OSMOTR_NEVROLOGA,
//                dateFormat.parse("02.01.2015"),
//                dateFormat.parse("02.01.2015"),
//                treatment));
//
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 0);
//    }
//
//    @Test
//    public void TestOneServiceOfNevrologAndRedundantService() throws ParseException {
//        treatment.getUslugi().put(1d, new Service(ser,
//                dateFormat.parse("01.01.2015"),
//                dateFormat.parse("01.01.2015"),
//                treatment));
//
//        treatment.getUslugi().put(3d, new Service(treatment.OSMOTR_NEVROLOGA,
//                dateFormat.parse("02.01.2015"),
//                dateFormat.parse("02.01.2015"),
//                treatment));
//
//        List<String> strings = treatment.checkUslugi();
//        assertEquals(strings.size(), 1);
//        assertEquals(strings.get(0), "1111\tИванов Иван Иванович\t01.01.2015\t лишнее обращение к врачу-неврологу.");
//    }

}