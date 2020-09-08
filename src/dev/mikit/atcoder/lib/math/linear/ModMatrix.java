package dev.mikit.atcoder.lib.math.linear;

import dev.mikit.atcoder.lib.math.mod.ModMath;

import java.util.Arrays;

public class ModMatrix {

    private final ModMath mod;

    private final int n;
    private final int m;
    private long[][] mat;
    private final int rank;

    public ModMatrix(ModMath mod, long[][] matrix, boolean ignoreLastCol) {
        this.mod = mod;
        this.mat = matrix;
        this.n = mat.length;
        this.m = mat[0].length;
        long modulo = mod.getModulo();

        int rank = 0;
        for (int col = 0; col < m; col++) {
            if (ignoreLastCol && col == m - 1) continue;

            int pivot = -1;
            for (int row = rank; row < n; row++) {
                if (mat[row][col] != 0) {
                    pivot = row;
                }
            }
            if (pivot == -1) continue;
            swap(pivot, rank);

            long inv = mod.inv(mat[rank][col]);
            for (int c = 0; c < m; c++) {
                mat[rank][c] *= inv;
                mat[rank][c] %= modulo;
            }
            if (mat[rank][col] != 1L) {
                throw new RuntimeException("Something went wrong");
            }

            for (int row = 0; row < n; row++) {
                if (row != rank && mat[row][col] != 0) {
                    long fac = mat[row][col];
                    for (int c = 0; c < m; c++) {
                        mat[row][c] -= mat[rank][c] * fac;
                        mat[row][c] %= modulo;
                        mat[row][c] += modulo;
                        mat[row][c] %= modulo;
                    }
                }
            }
            rank++;
        }
        this.rank = rank;
    }

    private void swap(int row1, int row2) {
        long[] temp = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = temp;
    }

    public static long[] solve(ModMath mod, long[][] a, long[] b) {
        int n = a.length, m = a[0].length;
        if (n != b.length) {
            throw new IllegalArgumentException("Size mismatch");
        }
        long[][] ext = new long[n][m + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(a[i], 0, ext[i], 0, m);
            ext[i][m] = b[i];
        }

        //System.out.println("Before");
        //Arrays.stream(ext).map(Arrays::toString).forEach(System.out::println);

        ModMatrix mat = new ModMatrix(mod, ext, true);
        for (int i = mat.rank; i < n; i++) {
            if (ext[i][m] != 0) {
                throw new IllegalStateException("No solution found");
            }
        }

        //System.out.println("Afrte");
        //Arrays.stream(mat.mat).map(Arrays::toString).forEach(System.out::println);

        long[] res = new long[m];
        for (int i = 0; i < mat.rank; i++) {
            res[i] = ext[i][m];
        }
        return res;
    }
}
