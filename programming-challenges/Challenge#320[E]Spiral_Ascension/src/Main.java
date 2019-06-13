import java.util.Scanner;

public class Main {

    /*
     * https://www.reddit.com/r/dailyprogrammer/comments/6i60lr/20170619_challenge_320_easy_spiral_ascension/
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int m = scanner.nextInt();

        int count = 1;
        int[][] result = new int[m][m];

        for (int i = 0; i < m; i++) {
            for (int x = i; x < m - i; x++) {
                if(result[i][x] == 0) result[i][x] = count++;
            }

            for (int x = i+1; x < m-i; x++) {
                if(result[x][m-(i+1)] == 0) result[x][m-(i+1)] = count++;
            }

            for (int x = m-i-2; x >= 0; x--) {
                if(result[m-(i+1)][x] == 0) result[m-(i+1)][x] = count++;
            }

            for (int x = m-i-2; x > 0; x--) {
                if(result[x][i] == 0) result[x][i] = count++;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%02d ", result[i][j]);
            }
            System.out.println();
        }
    }
}
