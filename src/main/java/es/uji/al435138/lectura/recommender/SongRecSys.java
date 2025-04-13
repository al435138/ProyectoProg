package es.uji.al435138.lectura.recommender;

import es.uji.al435138.lectura.machinelearning.Algorithm;
import es.uji.al435138.lectura.machinelearning.KNN;
import es.uji.al435138.lectura.machinelearning.KMeans;
import es.uji.al435138.lectura.csv.CSV;
import es.uji.al435138.lectura.table.Table;
import es.uji.al435138.lectura.recommender.RecSys;
import es.uji.al435138.lectura.machinelearning.EuclideanDistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

class SongRecSys {
    private RecSys recsys;

    SongRecSys(String method) throws Exception, LikedItemNotFoundException {
        String sep = System.getProperty("file.separator");
        String ruta = "recommender";

        // File names (could be provided as arguments to the constructor to be more general)
        Map<String,String> filenames = new HashMap<>();
        filenames.put("knn"+"train",ruta+sep+"songs_train.csv");
        filenames.put("knn"+"test",ruta+sep+"songs_test.csv");
        filenames.put("kmeans"+"train",ruta+sep+"songs_train_withoutnames.csv");
        filenames.put("kmeans"+"test",ruta+sep+"songs_test_withoutnames.csv");

        // Algorithms
        Map<String, Algorithm> algorithms = new HashMap<>();
        if (method.equals("knn")) {
            algorithms.put("knn", new KNN(new EuclideanDistance()));
        } else if (method.equals("kmeans")) {
            algorithms.put("kmeans", new KMeans(15, 200, 4321, new EuclideanDistance()));
        }

        // Tables
        Map<String, Table> tables = new HashMap<>();
        String [] stages = {"train", "test"};
        CSV csv = new CSV();
        for (String stage : stages) {
            tables.put("knn" + stage, csv.readTableWithLabels(filenames.get("knn" + stage)));
            tables.put("kmeans" + stage, csv.readTable(filenames.get("kmeans" + stage)));
        }

        // Names of items
        List<String> names = readNames(ruta+sep+"songs_test_names.csv");

        // Start the RecSys
        this.recsys = new RecSys(algorithms.get(method));
        this.recsys.train(tables.get(method+"train"));
        this.recsys.initialise(tables.get(method+"test"), names);

        // Given a liked item, ask for a number of recomendations
        String liked_name = "Lootkemia";
        List<String> recommended_items = this.recsys.recommend(liked_name,5);

        // Display the recommendation text (to be replaced with graphical display with JavaFX implementation)
        reportRecommendation(liked_name,recommended_items);
    }

    private List<String> readNames(String fileOfItemNames) throws IOException, URISyntaxException {
        String path = getClass().getClassLoader().getResource(fileOfItemNames).toURI().getPath();

        List<String> names = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            names.add(scanner.nextLine());
        }
        scanner.close();
        return names;
    }

    private void reportRecommendation(String liked_name, List<String> recommended_items) {
        System.out.println("If you liked \""+liked_name+"\" then you might like:");
        for (String name : recommended_items)
        {
            System.out.println("\t * "+name);
        }
    }

    public static void main(String[] args) throws Exception, LikedItemNotFoundException {
        new SongRecSys("knn");
        new SongRecSys("kmeans");
    }
}
