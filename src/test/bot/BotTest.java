package bot;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Mike Moolenaar
 */
public class BotTest {

    private Bot bot;

    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException {
        this.bot = new Bot("1", new DataParser());
    }

    @Test
    public void send() {
        String unknownText = "cow";

        this.bot.send(unknownText);

        assertThat(bot.getMessage(), startsWith("What is " + unknownText));
    }

    @Test
    public void sendNothing() {
        String nothing = "   ";

        this.bot.send(nothing);

        assertThat(bot.getMessage(), startsWith("What do you want to talk about?"));
    }

    @Test
    public void parse() {
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword("cow", "22", "", "", "", 0, ""));
        keywords.add(new Keyword("chicken", "22", "", "", "", 0, ""));

        Keyword keyword1 = bot.parse("What is a chicken or a cow?", keywords);
        Keyword keyword2 = bot.parse("What is a cat?", keywords);
        Keyword keyword3 = bot.parse("What is a chicken?", keywords);

        assertThat(keyword1.keyword, is("cow")); // Should be a cow beccause cow was defined in the keywords array first
        assertThat(keyword2, is(nullValue()));
        assertThat(keyword3.keyword, is("chicken"));
    }

    @Test
    public void parseAndLearn() {
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword("(.*) is the best animal", "22", "", "", "best_animal", 0, ""));

        bot.parse("dog is the best animal", keywords);
        bot.parse("cat is the best animal", keywords);

        assertThat(bot.getDictionary().get("best_animal"), is("cat"));
    }

    @Test
    public void getMatches() {
        Keyword keyword = new Keyword("Hello (.*)", "22", "", "", "subject", 1, "");
        Keyword keywordAge = new Keyword(".*I am ([0-9]+) years old.*", "22", "", "", "age", 80, "");

        int numberOfMatches1 = bot.getMatches("Hello darkness", keyword);
        int numberOfMatches2 = bot.getMatches("Goodbye darkness", keyword);
        int numberOfMatchesAge1 = bot.getMatches("I am 9 years old", keywordAge);
        int numberOfMatchesAge2 = bot.getMatches("I am - years old", keywordAge);

        assertThat(numberOfMatches1, is(1));
        assertThat(numberOfMatches2, is(-1));
        assertThat(numberOfMatchesAge1, is(80));
        assertThat(numberOfMatchesAge2, is(-1));
    }

    @Test
    public void replaceMatches() {
        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword("My name is (.*)", "22", "", "", "name", 1, ""));

        bot.parse("My name is James", keywords);
        String output1 = bot.replaceMatches("The name is bond, [name] bond");
        String output2 = bot.replaceMatches("[name]_[name]_[name]");

        assertThat(output1, is("The name is bond, james bond"));
        assertThat(output2, is("james_james_james"));
    }
}