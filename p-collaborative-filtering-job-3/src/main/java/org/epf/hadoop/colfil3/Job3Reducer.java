package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.TreeSet;

public class Job3Reducer extends Reducer<Text, UserRecommendation, Text, Text> {

    // Nombre maximum de recommandations qu'on veut garder par utilisateur
    private static final int MAX_RECOMMENDATIONS = 5;

    private Text result = new Text();

    @Override
    protected void reduce(Text userId, Iterable<UserRecommendation> recommendations, Context context)
            throws IOException, InterruptedException {

        // On utilise un TreeSet pour garder les 5 meilleures recommandations
        TreeSet<UserRecommendation> bestRecommendations = new TreeSet<>((rec1, rec2) -> {
            // On compare d'abord le nombre d'amis en commun (ordre décroissant)
            int compareByFriends = -Integer.compare(rec1.getCommonFriends(), rec2.getCommonFriends());

            // Si le nombre d'amis est égal, on trie par ID d'utilisateur
            if (compareByFriends == 0) {
                return rec1.getRecommendedId().compareTo(rec2.getRecommendedId());
            }
            return compareByFriends;
        });

        // Pour chaque recommandation reçue
        for (UserRecommendation rec : recommendations) {
            UserRecommendation copy = new UserRecommendation(
                    rec.getUserId(),
                    rec.getRecommendedId(),
                    rec.getCommonFriends()
            );

            bestRecommendations.add(copy);

            // Si on dépasse 5 recommandations, on enlève la dernière (la moins bonne)
            if (bestRecommendations.size() > MAX_RECOMMENDATIONS) {
                bestRecommendations.pollLast();
            }
        }

        if (!bestRecommendations.isEmpty()) {
            // On crée la chaîne de résultat au format "ami1(nbAmisCommuns), ami2(nbAmisCommuns), ..."
            StringBuilder resultStr = new StringBuilder();

            for (UserRecommendation rec : bestRecommendations) {
                if (resultStr.length() > 0) {
                    resultStr.append(", ");
                }
                resultStr.append(rec.getRecommendedId())
                        .append("(")
                        .append(rec.getCommonFriends())
                        .append(")");
            }

            result.set(resultStr.toString());
            context.write(userId, result);
        }
    }
}

