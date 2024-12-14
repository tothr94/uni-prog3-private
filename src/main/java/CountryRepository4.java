import base.Repository;
import countries.model.Country;
import countries.model.Region;
import countries.query.CountryQueries3;
import countries.query.CountryQueries4;
import lombok.NonNull;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CountryRepository4
        extends Repository<Country>
        implements CountryQueries4 {

    public CountryRepository4() throws IOException {
        super(Country.class);
    }

    @Override
    public Map<String, Country> getCountriesByCodes() {
        /*
        Map<String, Country> countries = new HashMap<>();
        for (Country country : getAll()) {
            countries.put(country.getCode(), country);
        }
        return countries;
         */

        /*
        return getAll().stream()
                .collect(
                        Collector.of(
                                HashMap::new,
                                (HashMap<String, Country> res, Country c) -> res.put(c.getCode(), c),
                                (a, b) -> {
                                    a.putAll(b);
                                    return a;
                                })
                );
         */

        /*
        return getAll().stream()
                .collect(Collectors.toMap(Country::getCode, c -> c));
         */

        /*
        return getAll().stream()
                .collect(Collectors.toMap(Country::getCode, Function.identity()));
         */

        /*
        return getAll().stream()
                .collect(Collectors.toUnmodifiableMap(Country::getCode, Function.identity()));
         */

        return getAll().stream()
                .collect(
                        Collector.of(
                                HashMap::new,
                                (HashMap<String, Country> res, Country c) -> res.put(c.getCode(), c),
                                (a, b) -> {
                                    a.putAll(b);
                                    return a;
                                },
                                Collections::unmodifiableMap
                        )
                );
    }

    @Override
    public Map<Region, Long> getCountOfCountriesByRegions() {
        /*
        return getAll().stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.counting())
                );
         */

        /*
        return getAll().stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                group -> (long) group.size()
                        )
                ));
         */

        return getAll().stream()
                .collect(Collector.of(
                        HashMap::new,
                        (HashMap<Region, Long> res, Country c) -> res.put(c.getRegion(), res.getOrDefault(c.getRegion(), 0L) + 1),
                        (a, b) -> {
                            for (Map.Entry<Region, Long> entry : b.entrySet()) {
                                a.put(entry.getKey(), a.getOrDefault(entry.getKey(), 0L) + entry.getValue());
                            }
                            return a;
                        }
                ));
    }

    @Override
    public Map<Region, Set<Country>> getCountriesByRegions() {
        return Map.of();
    }

    @Override
    public Map<Region, Optional<Country>> getMostPopulousCountryByRegions() {
        return Map.of();
    }

    @Override
    public Map<Region, List<Country>> getCountriesByRegionsOrderByCapitals() {
        return Map.of();
    }

    @Override
    public Map<Region, Set<Country>> getCountriesByRegionsFilterByPopulation(long lowerBound, long upperBound) {
        return Map.of();
    }

    @Override
    public Map<Region, Map<String, Country>> getCountriesByRegionsAndCodes() {
        return getAll().stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.toMap(
                                Country::getCode,
                                Function.identity()
                        )
                ));
    }

    @Override
    public Map<Region, Map<String, Set<Country>>> getCountriesByRegionsAndFirstLetters() {
        return getAll().stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.groupingBy(
                                country -> country.getName().substring(0, 1),
                                Collectors.toSet()
                        )
                ));
    }

    @Override
    public Map<String, Map<Region, Set<Country>>> getCountriesByFirstLettersAndRegions() {
        return Map.of();
    }

    @Override
    public Map<Region, Set<String>> getLocalizedCountryNamesByRegions(@NonNull String locale) {
        return Map.of();
    }

    @Override
    public Map<String, Set<String>> getCountryNamesByLocales() {
        return Map.of();
    }

    @Override
    public Map<String, Set<String>> getCountryNamesByLocales(@NonNull Region region) {
        return Map.of();
    }

    @Override
    public Map<Region, Optional<String>> getFirstLocalizedCountryNamesByRegions(@NonNull String locale) {
        return Map.of();
    }

    @Override
    public Map<Region, Optional<Country>> getFirstLocalizedCountriesByRegions(@NonNull String locale) {
        return Map.of();
    }
}