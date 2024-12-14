import base.Repository;
import countries.model.Country;
import countries.model.Region;
import countries.query.CountryQueries6;
import countries.query.CountryQueries7;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class CountryRepository6
        extends Repository<Country>
        implements CountryQueries6 {

    public CountryRepository6() throws IOException {
        super(Country.class);
    }

    public static void main(String[] args) throws IOException {
        final var repo = new CountryRepository6();
        System.out.println(repo.getTheGreatestCountry());
    }

    @Override
    public Optional<Country> getTheGreatestCountry() {
        return getAll().stream()
                .filter(country -> country.getArea() != null)
                .max(Comparator.comparing(Country::getArea));
    }

    @Override
    public DoubleSummaryStatistics getStatisticsOfAreas() {
        return getAll().stream()
                .map(Country::getArea)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();
    }

    // FIXME
    @Override
    public Optional<BigDecimal> getTotalAreaAsOptional() {
        return getAll().stream()
                .map(Country::getArea)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalAreaAsValue() {
        return getAll().stream()
                .map(Country::getArea)
                .filter(Objects::nonNull)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}
