package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Job2Mapper extends Mapper<LongWritable, Text, UserPair, IntWritable>  {

    private final IntWritable ONE = new IntWritable(1);
    private final IntWritable MINUS_ONE = new IntWritable(-1);

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        // Format d'entrée : utilisateur relation1,relation2,relation3,...
        String[] parts = value.toString().split("\\s+");
        if (parts.length != 2) return; // si pas modulo 2, on ignore

        String user = parts[0];
        List<String> friends = Arrays.asList(parts[1].split(",")); // liste des amis

        // Pour chaque ami, on émet une paire (user, ami) avec la valeur -1
        for (String friend : friends) {
            context.write(new UserPair(user, friend), MINUS_ONE);
        }

        // Pour chaque paire d'amis, on émet une paire (ami1, ami2) avec la valeur 1
        for (int i = 0; i < friends.size(); i++) {
            String rel1 = friends.get(i);
            for (int j = i + 1; j < friends.size(); j++) {
                String rel2 = friends.get(j);
                // Ces deux relations ont l'utilisateur courant en commun
                context.write(new UserPair(rel1, rel2), ONE);
            }
        }
    }
}
