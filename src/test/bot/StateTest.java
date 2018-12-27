package bot;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Mike Moolenaar
 */
public class StateTest {
    @Test
    public void testRandomMessageGet() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Message1");
        messages.add("Message1");
        State state = new State("1", messages, null);

        String m1 = state.getMessage();
        String m2 = state.getMessage();

        assertThat(messages.contains(m1), is(true));
        assertThat(messages.contains(m2), is(true));

    }
}