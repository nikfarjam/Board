package com.mehdi.reader;

import com.mehdi.error.CommandReaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class FileCommandReader implements CommandReader {

    private Path path;

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public Stream<String> getCommandStream() throws CommandReaderException {
        if (path == null) {
            throw new CommandReaderException("No file");
        }
        try {
            return Files.lines(path);
        } catch (IOException e) {
            throw new CommandReaderException("Error in reading file: " + path.getFileName());
        }
    }

    @Override
    public Iterator<String> getCommandIterable() throws CommandReaderException {
        return getCommandStream().iterator();
    }
}
