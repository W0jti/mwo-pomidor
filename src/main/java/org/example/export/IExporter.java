package org.example.export;

import java.io.IOException;

public interface IExporter {
    void export(String filename) throws IOException;
    void exportDetailed(String filename) throws IOException;
}
