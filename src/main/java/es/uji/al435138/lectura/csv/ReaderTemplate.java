package es.uji.al435138.lectura.csv;

import es.uji.al435138.lectura.table.Table;

import java.io.Reader;
import java.util.Scanner;

public abstract class ReaderTemplate <T> {

    protected Scanner scanner;
    protected T table;
    protected String source;

    public ReaderTemplate(String source) {
        this.source = source;
    }

    abstract void openSource(String source);
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void closeSource();
    abstract boolean hasMoreData();
    abstract String getNextData();
    abstract void createTable();



    public final T readTableFromSource() {
        openSource(source);
        createTable();
        if (scanner.hasNextLine()) {
            String headers = scanner.nextLine();
            processHeaders(headers);
        }
        while (hasMoreData()) {
            String data = getNextData();
            processData(data);
        }
        closeSource();
        return table;
    }
}
