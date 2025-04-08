package es.uji.al435138.lectura.csv;

import es.uji.al435138.lectura.table.Table;

public abstract class ReaderTemplate {

    abstract void openSource(String source);
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void closeSource();
    abstract boolean hasMoreData();
    abstract String getNextData();

    public final T readTableFromSource(){
        //Pasos del algoritmo
        openSource(nFich);
        String file = getClass().getClassLoader().getResource(nFich).toURI().getPath();

    }
}
