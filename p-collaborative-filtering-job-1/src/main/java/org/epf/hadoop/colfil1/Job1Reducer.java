package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Job1Reducer extends Reducer<Text, Text, Text, Text>{

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> relationUsers = new ArrayList<>();

        for (Text value : values) {
            String relation = value.toString().trim();

            if (!relation.isEmpty()) {
                relationUsers.add(relation);
            }
        }

        if (!relationUsers.isEmpty()) {
            context.write(key, new Text(String.join(",", relationUsers)));
        }
    }

}
