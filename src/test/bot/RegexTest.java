package bot;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Mike Moolenaar
 */
public class RegexTest {

    @Test
    public void match() {
        String match1 = Regex.match("([0-9]+)", "Hallo ik ben Henk, hoe gaat het?");
        String match2 = Regex.match("ik ben ([0-9]+).+", "ik ben 9 jaar oud");
        String match3 = Regex.match("Hallo (.*)", "Hallo peter");

        assertThat(match1, is(""));
        assertThat(match2, is("9"));
        assertThat(match3, is("peter"));
    }

    @Test
    public void clear() {
        String str1 = Regex.clear("Ik ben [naam]");
        String str2 = Regex.clear("[]]]");

        assertThat(str1, is("Ik ben "));
        assertThat(str2, is(""));
    }
}