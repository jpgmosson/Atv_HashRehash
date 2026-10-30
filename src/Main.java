public class Main {

    public static int funcaoHash(int chave) {
        return chave % 100;
    }

    public static int rehash(int i) {
        return (i + 1) % 100;
    }

    public static int busca(int[] tabela, int chave) {
        int i = funcaoHash(chave);
        int passos = 0;

        while (tabela[i] != chave && tabela[i] != -1 && passos < 100) {
            i = rehash(i);
            passos++;
        }

        if (tabela[i] == chave) {
            return i;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        int[] tabela = new int[100];

        for (int i = 0; i < 100; i++) {
            tabela[i] = -1;
        }

        java.util.Random random = new java.util.Random(1090);

        int[] colisoes = new int[100];
        int[] chaves = new int[100];
        int totalColisoes = 0;
        int ocupadas = 0;
        int rejeitados = 0;

        for (int n = 0; n < 100; n++) {
            int numero = random.nextInt(100001);

            if (ocupadas >= 90) {
                rejeitados++;
            } else {
                int i = funcaoHash(numero);
                int colisoesDeNumero = 0;

                while (tabela[i] != -1) {
                    colisoesDeNumero++;
                    totalColisoes++;
                    i = rehash(i);
                }

                tabela[i] = numero;
                chaves[ocupadas] = numero;
                colisoes[ocupadas] = colisoesDeNumero;
                ocupadas++;
            }
        }

        int maior1 = -1;
        int maior2 = -1;
        int maior3 = -1;
        int col1 = -1;
        int col2 = -1;
        int col3 = -1;

        for (int k = 0; k < ocupadas; k++) {
            if (colisoes[k] > col1) {
                col3 = col2;
                maior3 = maior2;
                col2 = col1;
                maior2 = maior1;
                col1 = colisoes[k];
                maior1 = chaves[k];
            } else if (colisoes[k] > col2) {
                col3 = col2;
                maior3 = maior2;
                col2 = colisoes[k];
                maior2 = chaves[k];
            } else if (colisoes[k] > col3) {
                col3 = colisoes[k];
                maior3 = chaves[k];
            }
        }

        System.out.println("Elementos inseridos: " + ocupadas);
        System.out.println("Elementos rejeitados (tabela em 90%): " + rejeitados);
        System.out.println("Total de colisoes: " + totalColisoes);
        System.out.println("Tres que mais colidiram:");
        System.out.println("1) " + maior1 + " (" + col1 + " colisoes)");
        System.out.println("2) " + maior2 + " (" + col2 + " colisoes)");
        System.out.println("3) " + maior3 + " (" + col3 + " colisoes)");

        System.out.print("Posicoes livres: ");
        for (int p = 0; p < 100; p++) {
            if (tabela[p] == -1) {
                System.out.print(p + " ");
            }
        }
        System.out.println();

        System.out.println("Busca dos tres que mais colidiram:");
        System.out.println(maior1 + " -> posicao " + busca(tabela, maior1));
        System.out.println(maior2 + " -> posicao " + busca(tabela, maior2));
        System.out.println(maior3 + " -> posicao " + busca(tabela, maior3));
    }
}