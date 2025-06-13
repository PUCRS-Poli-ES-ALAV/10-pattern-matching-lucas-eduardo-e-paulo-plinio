public class PatternMatchingComparison {
    public static int naive(String s1, String s2, int[] iteracoes, int[] instrucoes) {
        int n = s1.length();
        int m = s2.length();
        for (int i = 0; i <= n - m; i++) {
            instrucoes[0]++;
            boolean match = true;
            for (int j = 0; j < m; j++) {
                instrucoes[0]++;
                iteracoes[0]++;
                if (s1.charAt(i + j) != s2.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match)
                return i;
        }
        return -1;
    }

    private static final int R = 256;
    private static final long Q = 1000000007;

    public static int rabinKarp(String pat, String txt, int[] iteracoes, int[] instrucoes) {
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

    public static int rabinKarpRolling(String pat, String txt, int[] iteracoes, int[] instrucoes) {
        int M = pat.length();
        int N = txt.length();
        long RM = 1;
        for (int i = 1; i <= M - 1; i++)
            RM = (RM * R) % Q;
        long patHash = hash(pat, M, instrucoes, iteracoes);
        long txtHash = hash(txt.substring(0, M), M, instrucoes, iteracoes);
        for (int i = 0; i <= N - M; i++) {
            instrucoes[0]++;
            if (patHash == txtHash) {
                instrucoes[0]++;
                if (txt.substring(i, i + M).equals(pat)) {
                    return i;
                }
            }
            if (i < N - M) {
                txtHash = (txtHash + Q - RM * txt.charAt(i) % Q) % Q;
                txtHash = (txtHash * R + txt.charAt(i + M)) % Q;
                iteracoes[0] += 2;
                instrucoes[0] += 2;
            }
        }
        return -1;
    }

    public static int kmp(String pat, String txt, int[] iteracoes, int[] instrucoes) {
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
        int len = 0, i = 1;
        lps[0] = 0;
        while (i < M) {
            instrucoes[0]++;
            iteracoes[0]++;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0)
                    len = lps[len - 1];
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    public static void runAll(String s1, String s2, String caso) {
        System.out.println("==== " + caso + " ====");
        String[] nomes = { "Naive", "Rabin-Karp", "Rabin-Karp Rolling", "KMP" };
        int[][] resultados = new int[4][3];
        double[] tempos = new double[4];

        int[] it = { 0 }, ins = { 0 };
        long ini = System.nanoTime();
        int pos = naive(s1, s2, it, ins);
        long fim = System.nanoTime();
        resultados[0][0] = pos;
        resultados[0][1] = it[0];
        resultados[0][2] = ins[0];
        tempos[0] = (fim - ini) / 1_000_000.0;

        it[0] = 0;
        ins[0] = 0;
        ini = System.nanoTime();
        pos = rabinKarp(s2, s1, it, ins);
        fim = System.nanoTime();
        resultados[1][0] = pos;
        resultados[1][1] = it[0];
        resultados[1][2] = ins[0];
        tempos[1] = (fim - ini) / 1_000_000.0;

        it[0] = 0;
        ins[0] = 0;
        ini = System.nanoTime();
        pos = rabinKarpRolling(s2, s1, it, ins);
        fim = System.nanoTime();
        resultados[2][0] = pos;
        resultados[2][1] = it[0];
        resultados[2][2] = ins[0];
        tempos[2] = (fim - ini) / 1_000_000.0;

        it[0] = 0;
        ins[0] = 0;
        ini = System.nanoTime();
        pos = kmp(s2, s1, it, ins);
        fim = System.nanoTime();
        resultados[3][0] = pos;
        resultados[3][1] = it[0];
        resultados[3][2] = ins[0];
        tempos[3] = (fim - ini) / 1_000_000.0;

        System.out.printf("%-20s %-10s %-15s %-15s %-15s\n", "Algoritmo", "Posição", "Iterações", "Instruções",
                "Tempo (ms)");
        for (int i = 0; i < 4; i++) {
            System.out.printf("%-20s %-10d %-15d %-15d %-15.3f\n", nomes[i], resultados[i][0], resultados[i][1],
                    resultados[i][2], tempos[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String s1 = "ABCDCBDCBDACBDABDCBADF";
        String s2 = "ADF";
        runAll(s1, s2, "Pequeno");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500_000; i++)
            sb.append('A');
        s1 = sb.toString() + "PATTERN";
        s2 = "PATTERN";
        runAll(s1, s2, "Grande (final)");

        s1 = "PATTERN" + sb.toString();
        runAll(s1, s2, "Grande (início)");

        s1 = sb.toString();
        runAll(s1, s2, "Grande (ausente)");
    }
}