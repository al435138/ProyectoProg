package es.uji.al435138.recommender;

import es.uji.al435138.machinelearning.Algorithm;
import es.uji.al435138.machinelearning.InvalidClusterNumberException;
import es.uji.al435138.table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecSys <T extends Table, U, V>{

    private Algorithm<T, U, V> algorithm;
    private List<String> testItemNames;
    private Map<String, V> estimatedLabels;
    private Table testData;


    public RecSys(Algorithm algorithm) {

        this.algorithm = algorithm;
        this.estimatedLabels = new HashMap<>();

    }

    public void train(Table trainData) throws InvalidClusterNumberException {

        algorithm.train((T) trainData);

    }

    public void initialise(Table testData, List<String> testItemNames){

        this.testData = testData;
        this.testItemNames = testItemNames;
        this.estimatedLabels.clear();

        for (int i = 0; i < testItemNames.size(); i++){

            U sample = (U) testData.getRowAt(i).getData();
            V label = algorithm.estimate(sample);
            estimatedLabels.put(testItemNames.get(i), label);

        }

    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {

        if(!estimatedLabels.containsKey(nameLikedItem)){
            throw new LikedItemNotFoundException();
        }

        V label = estimatedLabels.get(nameLikedItem);
        List<String> recommendations = new ArrayList<>();

        for (Map.Entry<String, V> entry : estimatedLabels.entrySet()){
            if (entry.getValue().equals(label) && !entry.getKey().equals(nameLikedItem)){
                recommendations.add(entry.getKey());
            }
        }

        return recommendations.subList(0, Math.min(numRecommendations, recommendations.size()));

    }

    public List<String> getItemNames() {
        return new ArrayList<>(estimatedLabels.keySet());
    }

}
