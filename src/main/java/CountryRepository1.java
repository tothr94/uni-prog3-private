import base.Repository;
import countries.model.Country;
import countries.model.Region;
import countries.query.CountryQueries1;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class CountryRepository1
        extends Repository<Country>
        implements CountryQueries1 {

    public CountryRepository1() throws IOException {
        super(Country.class);
    }

    @SneakyThrows
    public static void main(String[] args) {
        CountryRepository1 repository = new CountryRepository1();
        System.out.println(repository);
        Country c = repository.getAll().get(0);
        // System.out.println(new MapToPopulation().apply(c));
        System.out.println(repository.getMaximumPopulation());
        System.out.println(repository.getAveragePopulation());
        System.out.println(repository.getCountOfEuropeanCountries());

        repository.getOptionalCountryByCode("XX")
                .or(() -> repository.getOptionalCountryByCode("YY"))
                .map(Country::getName)
                .ifPresent(System.out::println);
    }

    private static class MapToPopulation
            implements ToLongFunction<Country> {

        @Override
        public long applyAsLong(Country value) {
            return value.getPopulation();
        }
    }

    @Override
    public long getMaximumPopulation() {
        /*
        return getAll().stream()
                .mapToLong(new MapToPopulation())
                .max()
                .getAsLong();
        */

        /*
        return getAll().stream()
                .map(Country::getPopulation)
                .reduce(
                        Long.MIN_VALUE,
                        Long::max
                );
         */

        return getAll().stream()
                .map(Country::getPopulation)
                .reduce(
                        Long::max
                )
                .orElseThrow();
    }

    @Override
    public double getAveragePopulation() {
        return getAll().stream()
                //.mapToLong(country -> country.getPopulation())
                .mapToLong(Country::getPopulation)
                .average()
                .getAsDouble();
    }

    public static class EuropeanPredicate
            implements Predicate<Country> {
        @Override
        public boolean test(Country country) {
            return country.getRegion() == Region.EUROPE;
        }
    }

    public static boolean isEuropean(Country country) {
        return country.getRegion() == Region.EUROPE;
    }

    @Override
    public long getCountOfEuropeanCountries() {
        Predicate<Country> predicate = c -> c.getRegion() == Region.EUROPE;


        return getAll().stream()
                .filter(predicate)
                .filter(CountryRepository1::isEuropean)
                .filter(new EuropeanPredicate())
                .filter(country -> country.getRegion() == Region.EUROPE)
                .filter(new Predicate<Country>() {
                    @Override
                    public boolean test(Country country) {
                        return country.getRegion() == Region.EUROPE;
                    }
                })
                .filter(Country::isEuropean)
                .count();

    }

    @Override
    public long getCountOfCountriesFilterByRegion(@NonNull Region region) {
        return getAll().stream()
                .filter(country -> country.getRegion() == region)
                .count();
    }

    @Override
    public long getPopulationByRegion(@NonNull Region region) {
        return 0;
    }

    @Override
    public boolean isPopulationExists(long population) {
        return getAll().stream()
                .anyMatch(country -> country.getPopulation() == population);
    }

    @Override
    public Country getCountryByCode(@NonNull String code) {
        return getAll().stream()
                .filter(country -> Objects.equals(country.getCode(), code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Optional<Country> getOptionalCountryByCode(@NonNull String code) {
        return getAll().stream()
                .filter(country -> Objects.equals(country.getCode(), code))
                .findFirst();
    }

    @Override
    public Optional<Country> getMostPopulousCountryByRegion(@NonNull Region region) {
        return getAll().stream()
                .filter(country -> country.getRegion() == region)
                .max(Comparator.comparing(Country::getPopulation));

        /*
        return getAll().stream()
                .filter(country -> country.getRegion() == region)
                .sorted(Comparator.comparing(Country::getPopulation, Comparator.reverseOrder()))
                .findFirst();

         */

        /*
        return getAll().stream()
                .filter(country -> country.getRegion() == region)
                .min(Comparator.comparing(Country::getPopulation, Comparator.reverseOrder()));

         */
    }

    @Override
    public Optional<Country> getFirstCountryByStartingLetter(char letter) {
        return Optional.empty();
    }

    @Override
    public String getCommaSeparatedOrderedCountryNames() {
        return getAll().stream()
                .map(Country::getName)
                .reduce(
                        "",
                        (a, b) -> a + "," + b
                );

        /*
        return getAll().stream()
                .map(Country::getName)
                .collect(Collectors.joining(","));
        /*
                .collect(Collector.of(
                        () -> new StringJoiner(","),
                        (sj, name) -> sj.add(name),
                        (sj1, sj2) -> sj1.merge(sj2),
                        sj -> sj.toString()
                ));
        /*
        .collect(Collector.of(
                        () -> new StringJoiner(","),
                        StringJoiner::add,
                        StringJoiner::merge,
                        StringJoiner::toString
                ));
         */
    }
}
