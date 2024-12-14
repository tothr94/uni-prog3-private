import base.Repository;
import countries.model.Country;
import countries.model.Region;
import countries.query.CountryQueries2;
import countries.query.CountryQueries7;

import java.io.IOException;
import java.util.*;

public class CountryRepository7
        extends Repository<Country>
        implements CountryQueries7 {

    public CountryRepository7() throws IOException {
        super(Country.class);
    }

    public static void main(String[] args) throws IOException {
        final var repo = new CountryRepository7();
        repo.getLongestTranslation();
    }

    @Override
    public Optional<String> getLongestTranslation() {
        return getAll().stream()
                .map(Country::getTranslations)
                .map(Map::values)
                .flatMap(Collection::stream)
                .max(Comparator.comparing(String::length));
    }

    @Override
    public Optional<String> getLongestItalianTranslation() {
        return getAll().stream()
                .map(Country::getTranslations)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .filter(entry -> Objects.equals(entry.getKey(), "IT"))
                .map(Map.Entry::getValue)
                .max(Comparator.comparing(String::length));
    }

    @Override
    public Optional<String> getLongestTranslationWithLanguageCode() {
        return getAll().stream()
                .map(Country::getTranslations)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .max(Comparator.comparing(entry -> entry.getValue().length()))
                .map(entry -> entry.getKey() + ":" + entry.getValue());
    }

    @Override
    public Optional<String> getCountryNameHavingMostOfELettersIgnoringCase() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCapitalHavingMostOfVowels() {
        return Optional.empty();
    }

    @Override
    public Map<Region, String> getCapitalsThatAreAlsoCountryNamesByRegions() {
        return Map.of();
    }
}
