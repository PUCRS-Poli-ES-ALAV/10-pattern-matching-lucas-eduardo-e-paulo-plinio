public class PatternMatchingRabinKarp {
    private static final int R = 256;
    private static final long Q = 1000000007;

    public static int search(String pat, String txt, int[] iteracoes, int[] instrucoes) {
        int M = pat.length();
        int N = txt.length();
        long patHash = hash(pat, M, instrucoes, iteracoes);

        for (int i = 0; i <= N - M; i++) {
            instrucoes[0]++;
            long txtHash = hash(txt.substring(i, i + M), M, instrucoes, iteracoes);
            if (patHash == txtHash) {
                instrucoes[0]++;
                if (txt.substring(i, i + M).equals(pat)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static long hash(String s, int M, int[] instrucoes, int[] iteracoes) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            iteracoes[0]++;
            instrucoes[0]++;
            h = (h * R + s.charAt(j)) % Q;
        }
        return h;
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500_000; i++)
            sb.append('A');
        String s1 = sb.toString() + "PATTERN";
        String s2 = "PATTERN";

        int[] iteracoes = { 0 };
        int[] instrucoes = { 0 };

        long start = System.nanoTime();
        int pos = search(s2, s1, iteracoes, instrucoes);
        long end = System.nanoTime();

        System.out.println("Posição: " + pos);
        System.out.println("Iterações: " + iteracoes[0]);
        System.out.println("Instruções: " + instrucoes[0]);
        System.out.println("Tempo de execução: " + ((end - start) / 1_000_000.0) + " ms");
    }
}