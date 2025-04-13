package es.uji.al435138.lectura.csv;

import es.uji.al435138.lectura.table.Table;

import java.io.File;
import java.util.Scanner;

abstract class FileReader<T extends Table> extends ReaderTemplate<T> {

    public FileReader(String source) {
        super(source);
    }

    @Override
    protected void openSource(String filename) {
        try {
            String path = getClass().getClassLoader().getResource(filename).getPath();
            scanner = new Scanner(new File(path));
        } catch (Exception e) {
            throw new RuntimeException("File not found: " + filename, e);
        }
    }

    @Override
    protected boolean hasMoreData() {
        return scanner.hasNextLine();
    }

    @Override
    protected String getNextData() {
        return scanner.nextLine();
    }

    @Override
    protected void closeSource() {
        scanner.close();
    }

}
