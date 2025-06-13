public class PatternMatchingKMP {
    public static int KMPSearch(String pat, String txt, int[] iteracoes, int[] instrucoes) {
        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int j = 0;

        computeLPSArray(pat, M, lps, iteracoes, instrucoes);

        int i = 0;
        while (i < N) {
            instrucoes[0]++;
            iteracoes[0]++;
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                return i - j;
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return -1;
    }

    private static void computeLPSArray(String pat, int M, int lps[], int[] iteracoes, int[] instrucoes) {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < M) {
            instrucoes[0]++;
            iteracoes[0]++;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500_000; i++)
            sb.append('A');
        String s1 = sb.toString()
                + "SDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFASDABVHKLDFABHVFLHJABVFDALBVHLJDFAVBFLJHVBFDAVHJLAVFAPATTERN";
        String s2 = "PATTERN";

        int[] iteracoes = { 0 };
        int[] instrucoes = { 0 };

        long start = System.nanoTime();
        int pos = KMPSearch(s2, s1, iteracoes, instrucoes);
        long end = System.nanoTime();

        System.out.println("Posição: " + pos);
        System.out.println("Iterações: " + iteracoes[0]);
        System.out.println("Instruções: " + instrucoes[0]);
        System.out.println("Tempo de execução: " + ((end - start) / 1_000_000.0) + " ms");
    }
}