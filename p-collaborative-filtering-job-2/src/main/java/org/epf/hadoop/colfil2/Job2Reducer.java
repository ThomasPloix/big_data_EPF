package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;


public class Job2Reducer extends Reducer<UserPair, IntWritable, Text, IntWritable>{

    private Text outputKey = new Text();
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(UserPair key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int sum = 0;

        boolean areFriends = false;
        // Parcourir toutes les valeurs
        for (IntWritable val : values) {
            if (val.get() == -1) {
                // S'ils sont déjà amis, on ne fait rien
                areFriends = true;
                break;
            }
            sum += val.get();
        }

        // s'ils ne sont pas amis Mais qu'ils ont des amis communs
        if (!areFriends && sum > 0) {
            outputKey.set(key.toString());
            result.set(sum);
            context.write(outputKey, result);
        }
    }
}