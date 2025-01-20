package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;


public class Job3Mapper extends Mapper<LongWritable,Text,Text, UserRecommendation> {
    private Text outputKey = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //user , user   Nombre user en commun
        String[] tokens = value.toString().split("\t");
        if (tokens.length != 2) return; // si pas 2 parts, on ignore

        String[] user = tokens[0].split(","); // user, user
        int commonFriends = Integer.parseInt(tokens[1]); // Nombre user en commun

        // Pour chaque paire d'amis, on émet une paire (ami1, ami2) avec le nombre d'amis en commun
        outputKey.set(user[0]);
        context.write(outputKey, new UserRecommendation(
                user[0], user[1], commonFriends));

        outputKey.set(user[1]);
        context.write(outputKey, new UserRecommendation(
                user[1], user[0], commonFriends));

        }
    }