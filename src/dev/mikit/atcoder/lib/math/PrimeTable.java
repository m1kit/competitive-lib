package dev.mikit.atcoder.lib.math;

import java.util.*;

public class PrimeTable {

    private final int[] divisor;
    private final List<Integer> primes = new ArrayList<>();

    public PrimeTable(int n) {
        this.divisor = new int[n];
        for (int i = 2; i < n; i++) {
            if (divisor[i] != 0) continue;
            divisor[i] = i;
            primes.add(i);
            for (int j = i + i; j < n; j += i) {
                if (divisor[j] == 0) divisor[j] = i;
            }
        }
    }

    public boolean isPrime(int x) {
        return divisor[x] == x;
    }

    public Map<Integer, Integer> primeFactorize(int x) {
        Map<Integer, Integer> res = new HashMap<>();
        while (divisor[x] != 0) {
            res.merge(divisor[x], 1, Integer::sum);
            x /= divisor[x];
        }
        return res;
    }

    public List<Integer> getPrimes() {
        return Collections.unmodifiableList(primes);
    }

    public List<Integer> getDivisors(int x) {
        Set<Map.Entry<Integer, Integer>> factors = primeFactorize(x).entrySet();
        int count = 1;
        for (Map.Entry<Integer, Integer> ent : factors) count *= ent.getValue() + 1;
        List<Integer> divisors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int t = i;
            int v = 1;
            for (Map.Entry<Integer, Integer> ent : factors) {
                int exp = t % (ent.getValue() + 1);
                for (int j = 0; j < exp; j++) v *= ent.getKey();
                t /= ent.getValue() + 1;
            }
            divisors.add(v);
        }
        return divisors;
    }

    public int countDivisors(int x) {
        int res = 1;
        while (x > 1) {
            int f = divisor[x], c = 0;
            while (divisor[x] == f) {
                x /= f;
                c++;
            }
            res *= c + 1;
        }
        return res;
    }

    public int getSize() {
        return divisor.length;
    }

}
