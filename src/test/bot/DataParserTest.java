package bot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Mike Moolenaar
 */
public class DataParserTest {

    private final String TEST_DATAFILES_PATH = "./src/test/bot/DataFiles/";
    private DataParser dpData1;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException {
        this.dpData1 = new DataParser(TEST_DATAFILES_PATH + "data1.xml");
    }

    @Test
    public void setupWithFileThatDoesNotExist() throws IOException, ParserConfigurationException, SAXException {
        expectedEx.expect(FileNotFoundException.class);
        expectedEx.expectMessage("Oops.xml could not be found");

        var dp = new DataParser("Oops.xml");
    }

    @Test(expected = FileNotFoundException.class)
    public void setupWithFileThatIsNotAnXml() throws IOException, ParserConfigurationException, SAXException {
        var dp = new DataParser("Oops");
    }

    @Test
    public void setupWithInvalidXml() throws ParserConfigurationException, SAXException, IOException {
        expectedEx.expect(ParserConfigurationException.class);
        expectedEx.expectMessage("Error in configuration");

        var dp = new DataParser(TEST_DATAFILES_PATH + "dataInvalid.xml");
    }

    @Test
    public void getState() {
        String state0 = "0";
        String stateDoesNotExist = "9000";

        var state = dpData1.getState(state0);
        var state1 = dpData1.getState(stateDoesNotExist);

        assertThat(state, isA(State.class));
        assertThat(state.getMessage(), is("Hello, My name is MajBot, what is your name?"));
        assertThat(state1, is(nullValue()));
    }

    @Test
    public void addState() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Message1");
        messages.add("Message1");
        State stateNew = new State("1", messages, null);

        var stateNewBefore = this.dpData1.getState("1");
        this.dpData1.addState(stateNew);
        var stateNewAfter = this.dpData1.getState("1");

        assertThat(stateNewBefore, is(nullValue()));
        assertThat(stateNewAfter, isA(State.class));
    }

    @Test
    public void addStateWithIdThatAlreadyExists() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Hello my name is Majbot!");
        messages.add("Oh hi there! I'm Majbot, nice to meet ya!");
        State stateNew = new State("0", messages, null);

        var stateNewBefore = this.dpData1.getState("0");
        this.dpData1.addState(stateNew);
        var stateNewAfter = this.dpData1.getState("0");

        assertThat(stateNewBefore.getMessage(), is("Hello, My name is MajBot, what is your name?"));
        assertThat(stateNewAfter.getMessage(), either(startsWith("Hello my")).or(startsWith("Oh hi there!")));
    }

    @Test
    public void getKeywords() throws Exception {
        var document = Helper.loadXMLFromString("\n" +
                "        <State id=\"0\">\n" +
                "        <message>Hello, My name is MajBot, what is your name?</message>\n" +
                "        <keywords>\n" +
                "            <keyword target=\"1\" variable=\"name\" points=\"2\">.*name is ([a-zA-z]+).*</keyword>\n" +
                "            <keyword target=\"1\" variable=\"name\">([a-zA-Z ]+)</keyword>\n" +
                "        </keywords>\n" +
                "    </State>");
        Element element = (Element)document.getElementsByTagName("keywords").item(0);

        ArrayList<Keyword> elements = (new DataParser()).getKeywords(element);

        assertThat(elements.size(), is(2));
        assertThat(elements.get(0).keyword, is(".*name is ([a-zA-z]+).*"));
        assertThat(elements.get(0).target, is("1"));
        assertThat(elements.get(0).learn, is(""));
    }

    @Test
    public void getInvalidAnswer() {
        for (int i = 0; i < 30; i++) {
            var answer = this.dpData1.getInvalidAnswer();
            assertThat(answer, either(is("Sorry, I don't understand?"))
                    .or(is("Oopsie I don't understand!"))
                    .or(is("Oopsie woopsie! I don't understand!?"))
            );
        }
    }
}