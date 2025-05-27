public class PatternMatching {
    public static int findFirstOccurrence(String s1, String s2, int[] iteracoes, int[] instrucoes) {
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
            if (match) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String s1 = "SADHUGOASHOGHNSBFGBFLASPATTERN";
        String s2 = "PATTERN";

        int[] iteracoes = { 0 };
        int[] instrucoes = { 0 };

        long start = System.nanoTime();
        int pos = findFirstOccurrence(s1, s2, iteracoes, instrucoes);
        long end = System.nanoTime();

        System.out.println("Posição: " + pos);
        System.out.println("Iterações: " + iteracoes[0]);
        System.out.println("Instruções: " + instrucoes[0]);
        System.out.println("Tempo de execução: " + ((end - start) / 1_000_000.0) + " ms");
    }
}