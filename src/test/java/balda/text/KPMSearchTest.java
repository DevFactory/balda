package balda.text;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Knuth–Morris–Pratt algo. For fun only
 */
public class KPMSearchTest {
    @Test
    public void indexOf() throws Exception {
        KMPSearch search = new KMPSearch();

        assertThat(search.indexOf("aba", "aba"), is(0));
        assertThat(search.indexOf("aba", "abba"), is(-1));
        assertThat(search.indexOf("aba", "aaba"), is(1));
        assertThat(search.indexOf("aba", "aabbabbbababba"), is(8));
        String longOne = "abrakadabrwabrakadabraabrakadahraabrakaydabraabeakadabraabrakadabraabrakadabra";
        String bigNeedle = "abrakadabra";
        int javaSearch = longOne.indexOf(bigNeedle);
        assertThat(search.indexOf(bigNeedle, longOne), is(javaSearch));
    }

    @Test
    public void performanceOfKPMvsJavaCore() throws Exception {
        int somewhatBigSize = 5_000_000;
        char[] veryBigHayStack = new char[somewhatBigSize];
        char[] veryLongNeedle = new char[somewhatBigSize - 500];
        for (int i = 0; i < somewhatBigSize - 1; i++) {
            veryBigHayStack[i] = 'a';
            if (i < veryLongNeedle.length)
                veryLongNeedle[i] = 'a';
        }
        veryBigHayStack[somewhatBigSize - 1] = 'b';
        veryLongNeedle[veryLongNeedle.length - 1] = 'b';

        // we use same input for java core and for KMP
        String haystack = new String(veryBigHayStack);
        String needle = new String(veryLongNeedle);

        long start = System.nanoTime();
        int coreIndexSearch = haystack.indexOf(needle);
        long coreTime = System.nanoTime() - start;
        System.out.println("Core time " + coreTime);

        KMPSearch kmpSearch = new KMPSearch();
        start = System.nanoTime();
        int kmpIndexSearch = kmpSearch.indexOf(needle, haystack);
        long kpmTime = System.nanoTime() - start;
        System.out.println("KPM  time " + kpmTime);

        assertThat(kmpIndexSearch, is(not(-1)));
        assertThat(kmpIndexSearch, is(equalTo(coreIndexSearch)));

        // on this dataset should be ten times faster
        assertThat(kpmTime, lessThan(coreTime / 10));
    }

    @Test
    public void prefixFunction() throws Exception {
        KMPSearch search = new KMPSearch();
        {
            int[] expected = new int[]{0, 0, 1, 2, 0};
            int k = 0;
            String string = "ababc";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
        {
            int[] expected = new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4};
            int k = 0;
            String string = "ababcabab";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
        {
            int[] expected = new int[]{0, 1, 2, 3, 4, 5};
            int k = 0;
            String string = "bbbbbb";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
        {
            int[] expected = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            int k = 0;
            String string = "abcdefgh";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
        {
            int[] expected = new int[]{0, 0, 0, 0, 1, 2, 0, 0, 1, 2, 3, 4, 5, 6, 0, 1};
            int k = 0;
            String string = "abcdabscabcdabia";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
        {
            int[] expected = new int[]{0, 0, 0, 0, 1, 2, 3, 1, 2, 3, 4, 5, 6, 7, 4, 5, 6};
            int k = 0;
            String string = "abcdabcabcdabcdab";
            int[] p = search.prefixFunction(string);
            assertThat("sample: " + string, p, is(expected));
        }
    }

}