package dev.mikit.atcoder.lib.math.geo.g2d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConvexHull {

    public static <T extends Vec2l> List<T> makeHull(List<T> points) {
        List<T> newPoints = new ArrayList<>(points);
        Collections.sort(newPoints);
        return makeHullPresorted(newPoints);
    }

    public static <T extends Vec2l> List<T> makeHullPresorted(List<T> points) {
        if (points.size() <= 2) return points;

        List<T> upperHull = new ArrayList<>();
        for (T p : points) {
            while (upperHull.size() >= 2) {
                T q = upperHull.get(upperHull.size() - 1);
                T r = upperHull.get(upperHull.size() - 2);
                if ((q.x - r.x) * (p.y - r.y) >= (q.y - r.y) * (p.x - r.x))
                    upperHull.remove(upperHull.size() - 1);
                else
                    break;
            }
            upperHull.add(p);
        }
        upperHull.remove(upperHull.size() - 1);

        List<T> lowerHull = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            T p = points.get(i);
            while (lowerHull.size() >= 2) {
                T q = lowerHull.get(lowerHull.size() - 1);
                T r = lowerHull.get(lowerHull.size() - 2);
                if ((q.x - r.x) * (p.y - r.y) >= (q.y - r.y) * (p.x - r.x))
                    lowerHull.remove(lowerHull.size() - 1);
                else
                    break;
            }
            lowerHull.add(p);
        }
        lowerHull.remove(lowerHull.size() - 1);

        if (!(upperHull.size() == 1 && upperHull.equals(lowerHull)))
            upperHull.addAll(lowerHull);
        return upperHull;
    }

}