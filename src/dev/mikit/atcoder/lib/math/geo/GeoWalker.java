package dev.mikit.atcoder.lib.math.geo;

import dev.mikit.atcoder.lib.util.function.BiIntConsumer;
import dev.mikit.atcoder.lib.util.function.BiLongConsumer;

public final class GeoWalker {

    private static final int[] DX = {1, 0, -1, 0, 1, 1, -1, -1};
    private static final int[] DY = {0, 1, 0, -1, 1, -1, -1, 1};

    private GeoWalker() {
    }

    public static void forEach4i(int x, int y, BiIntConsumer receiver) {
        for (int i = 0; i < 4; i++) {
            receiver.accept(x + DX[i], y + DY[i]);
        }
    }

    public static void forEach8i(int x, int y, BiIntConsumer receiver) {
        for (int i = 0; i < 8; i++) {
            receiver.accept(x + DX[i], y + DY[i]);
        }
    }

    public static void forEach4l(long x, long y, BiLongConsumer receiver) {
        for (int i = 0; i < 4; i++) {
            receiver.accept(x + DX[i], y + DY[i]);
        }
    }

    public static void forEach8l(long x, long y, BiLongConsumer receiver) {
        for (int i = 0; i < 8; i++) {
            receiver.accept(x + DX[i], y + DY[i]);
        }
    }
}
