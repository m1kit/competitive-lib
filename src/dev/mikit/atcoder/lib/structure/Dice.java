package dev.mikit.atcoder.lib.structure;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.*;

@Verified({
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3490578",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3491563",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3491573",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3491727",
})
public class Dice<T> {

    private final EnumMap<Face, T> map = new EnumMap<>(Face.class);

    public Dice(Dice<T> init) {
        map.putAll(init.map);
    }

    public Dice(Map<Face, T> init) {
        map.putAll(init);
    }

    public T get(Face face) {
        return map.get(face);
    }

    public void apply(Roll roll) {
        T tmp = map.get(roll.rot[0]);
        int n = roll.rot.length;
        for (int i = 1; i < n; i++) {
            map.put(roll.rot[i - 1], map.get(roll.rot[i]));
        }
        map.put(roll.rot[n - 1], tmp);
    }

    public List<Dice<T>> listAllStates() {
        List<Dice<T>> a = new ArrayList<>(24);
        Dice<T> cur = this;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                cur = new Dice<>(cur.map);
                a.add(cur);
                cur.apply(Roll.FORWARD);
            }
            if (i % 2 == 0) {
                cur.apply(Roll.LEFT);
            } else {
                cur.apply(Roll.HORIZONTAL_LEFT);
            }
        }
        return a;
    }

    public boolean equalsIgnoreRotation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dice<?> dice = (Dice<?>) o;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (map.equals(dice.map)) {
                    return true;
                }
                apply(Roll.FORWARD);
            }
            if (i % 2 == 0) {
                apply(Roll.LEFT);
            } else {
                apply(Roll.HORIZONTAL_LEFT);
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dice<?> dice = (Dice<?>) o;
        return Objects.equals(map, dice.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public enum Face {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK,
    }

    public enum Roll {
        FORWARD(Face.TOP, Face.BACK, Face.BOTTOM, Face.FRONT),
        BACKWARD(Face.FRONT, Face.BOTTOM, Face.BACK, Face.TOP),
        LEFT(Face.TOP, Face.RIGHT, Face.BOTTOM, Face.LEFT),
        RIGHT(Face.LEFT, Face.BOTTOM, Face.RIGHT, Face.TOP),
        HORIZONTAL_LEFT(Face.FRONT, Face.RIGHT, Face.BACK, Face.LEFT),
        HORIZONTAL_RIGHT(Face.LEFT, Face.BACK, Face.RIGHT, Face.FRONT),
        ;

        private final Face[] rot;

        Roll(Face ... rot) {
            this.rot = rot;
        }
    }
}
