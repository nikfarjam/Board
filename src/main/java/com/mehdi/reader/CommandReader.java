package com.mehdi.reader;

import com.mehdi.error.CommandReaderException;

import java.util.Iterator;
import java.util.stream.Stream;

public interface CommandReader {

    Stream<String> getCommandStream() throws CommandReaderException;

    Iterator<String> getCommandIterable() throws CommandReaderException;
}
