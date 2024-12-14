import base.Repository;
import countries.model.Country;
import countries.model.Region;
import countries.query.CountryQueries1;
import countries.query.CountryQueries2;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class CountryRepository2
        extends Repository<Country>
        implements CountryQueries2 {

    public CountryRepository2() throws IOException {
        super(Country.class);
    }

    @Override
    public List<Country> getCountriesOrderByPopulationDesc() {
        /*
        return getAll().stream()
                .sorted(Comparator.comparing(Country::getPopulation).reversed())
                .toList();

         */

        return getAll().stream()
                .sorted(Comparator.comparing(Country::getPopulation, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public List<Country> getCountriesOrderByLengthOfCapitalThenByPopulationDesc() {
        /*
        return getAll().stream()
                .sorted(
                        Comparator.<Country, Integer>comparing(
                                        country -> country.getCapital().length()
                                )
                                .thenComparing(Country::getPopulation)
                )
                .toList();
         */

        return getAll().stream()
                .sorted(
                        Comparator
                                .comparing(
                                        (Country country) -> country.getCapital().length()
                                )
                                .thenComparing(Country::getPopulation)
                )
                .toList();
    }

    @Override
    public List<Country> getCountriesOrderByLengthOfCapitalThenByCapital() {
        return getAll().stream()
                .sorted(
                        Comparator
                                .comparing(
                                        (Country country) -> country.getCapital().length()
                                )
                                .thenComparing(Country::getCapital)
                )
                .toList();
    }
}
