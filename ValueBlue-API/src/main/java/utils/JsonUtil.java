package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class JsonUtil {

    public Optional<String> readJsonFromFile(String filePath) {
        try {
            return Optional.of(Files.readString(Path.of(filePath)));
        } catch (IOException e) {
            LogClass.error("Failed to read JSON from file: " + filePath, e);
            return Optional.empty();
        }
    }
}
