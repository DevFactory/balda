package balda.text;

/**
 * Not really used in the project. Just to experiment a bit with the algorithm
 * Brought by anatolyd on 04.04.2017.
 */
public class KMPSearch implements SubstringSearch {

    private static final char UNALPHABETICAL_CHAR = '$';

    /**
     * @param needle   - the string to look for
     * @param haystack - the string where to look in
     * @return index of the first input, or -1 if not found
     */
    @Override
    public int indexOf(String needle, String haystack) {
        int length = needle.length();
        int[] prefixForNeedle = prefixFunction(needle);
        int needleState = 0, hayIndex;

        for (hayIndex = 0; hayIndex < haystack.length() && needleState < length; hayIndex++) {
            while ((needleState > 0) && (needle.charAt(needleState) != haystack.charAt(hayIndex))) {
                needleState = prefixForNeedle[needleState - 1];
            }
            if (needle.charAt(needleState) == haystack.charAt(hayIndex)) {
                ++needleState;
            }
        }
        return needleState == length ? hayIndex - length : -1;
    }

    /**
     * separated function for clarity only
     * @return prefixFunction array for a string
     */
    int[] prefixFunction(String string) {
        int[] p = new int[string.length()];
        int k = 0;
        for (int i = 1; i < string.length(); i++) {
            while ((k > 0) && (string.charAt(k) != string.charAt(i))) {
                k = p[k - 1];
            }
            if (string.charAt(k) == string.charAt(i)) {
                ++k;
            }

            p[i] = k;
        }
        return p;
    }
}
