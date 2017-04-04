package balda;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * ... by anatolyd on 04.04.2017 for good.
 */
public class SearchSubstringTest {
    @Test
    public void substringSearch() throws Exception {
        String target = "aabbaa";

        String firstSample = "aabbaaaa";
        assertTrue(firstSample.contains(target));

    }
}
