package org.example.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSearcher {

    public List<String> searchXlsFile(String folderPath) throws IOException {
        List<String> xlsFilePaths;
        Path path = Paths.get(folderPath);
        try (Stream<Path> walk = Files.walk(path)) {
            xlsFilePaths = walk
                    .filter(Files::isRegularFile)
                    .map(Object::toString)
                    .filter(f -> f.endsWith(".xls") || f.endsWith(".xlsx"))
                    .collect(Collectors.toList());
        }
        return xlsFilePaths;

    }
}
