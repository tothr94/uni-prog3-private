import countries.query.BasicQueries;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicQueriesImpl
        implements BasicQueries {

    private static final IntPredicate IS_PRIME = (int value) -> {
        if (value % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(value); i += 2) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    };

    @Override
    public Set<Integer> getOddNumberSetBetween(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .filter(v -> v % 2 == 1)
                .boxed()
                .collect(Collectors.toSet());
    }

    @Override
    public List<Integer> getOddNumberListBetween(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .filter(v -> v % 2 == 1)
                .boxed()
                .toList();
    }

    @Override
    public Set<Integer> getPrimeNumberSetBetween(int start, int end) {
        /*
        return IntStream.rangeClosed(start, end)
                .filter(BasicQueriesImpl.IS_PRIME)
                .boxed()
                .collect(Collectors.toSet());
         */

        return IntStream.rangeClosed(start, end)
                .mapToObj(BigInteger::valueOf)
                .filter(n -> n.isProbablePrime(5))
                .map(BigInteger::intValue)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Integer> getPrimeNumberListBetween(int start, int end) {
        return List.of();
    }

    @Override
    public Map<Integer, Integer> getSquaresBetween(int start, int end) {
        return Map.of();
    }

    @Override
    public Set<Integer> filterPrimes(Set<Integer> numbers) {
        return Set.of();
    }

    @Override
    public Set<Integer> getUniqueNumbers(List<Integer> numbers) {
        return Set.of();
    }

    @Override
    public List<Integer> mergeLists(List<Integer> first, List<Integer> second) {
        return List.of();
    }
}
