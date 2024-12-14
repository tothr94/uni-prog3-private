package countries.query;

import base.Repository;
import countries.model.Country;
import countries.model.Region;
import lombok.NonNull;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountryRepository3
        extends Repository<Country>
        implements CountryQueries3 {

    public CountryRepository3() throws IOException {
        super(Country.class);
    }

    @Override
    public Set<String> getCountryNames() {
        /*
        return getAll().stream()
                .map(Country::getName)
                .collect(Collectors.toSet());

        return getAll().stream()
                .map(Country::getName)
                .collect(Collectors.toUnmodifiableSet());

        return getAll().stream()
                .map(Country::getName)
                .collect(Collectors.toCollection(TreeSet::new));


        return getAll().stream()
                .map(Country::getName)
                .collect(Collectors.toCollection(HashSet::new));
         */

        return getAll().stream()
                .map(Country::getName)
                .collect(Collector.of(
                        HashSet::new,
                        HashSet::add,
                        (a, b) -> {
                            a.addAll(b);
                            return a;
                        }
                ));
    }

    @Override
    public List<String> getCapitalsOrderByName() {
        return getAll().stream()
                .map(Country::getCapital)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCapitalsOrderByNameDesc() {
        return getAll().stream()
                .map(Country::getCapital)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getNamesOfEuropeanCountries() {
        return getAll().stream()
                .filter(country -> country.getRegion() == Region.EUROPE)
                .map(Country::getCapital)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getNamesOfCountriesFilterByContinent(@NonNull Region region) {
        return Set.of();
    }

    @Override
    public Set<Country> getCountriesBelowPopulationLimit(int limit) {
        return Set.of();
    }

    @Override
    public Set<Long> getPopulationsByRegion(@NonNull Region region) {
        return Set.of();
    }

    @Override
    public Set<Country> getCountriesByPopulation(long population) {
        return Set.of();
    }

    @Override
    public Set<Country> getCountriesByPopulation(long lowerBound, long upperBound) {
        return Set.of();
    }
}